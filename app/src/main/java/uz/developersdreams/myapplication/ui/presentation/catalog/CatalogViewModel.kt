package uz.developersdreams.myapplication.ui.presentation.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.extensions.addProductToCart
import uz.developersdreams.myapplication.core.extensions.calculateValueOfProductsInCart
import uz.developersdreams.myapplication.core.extensions.changeChosenCategory
import uz.developersdreams.myapplication.core.extensions.getChosenCategoryId
import uz.developersdreams.myapplication.core.extensions.getChosenCategoryIdByIndex
import uz.developersdreams.myapplication.core.extensions.getSelectedTags
import uz.developersdreams.myapplication.core.extensions.onTagSelection
import uz.developersdreams.myapplication.core.extensions.removeProductFromCart
import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.core.util.Constants.PRODUCT_IN_CART
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.core.util.UiText
import uz.developersdreams.myapplication.feature.data.data_source.remote.status.Status
import uz.developersdreams.myapplication.feature.domain.use_case.category.CategoryUseCases
import uz.developersdreams.myapplication.feature.domain.use_case.product.ProductUseCases
import uz.developersdreams.myapplication.feature.domain.use_case.tag.TagUseCases
import uz.developersdreams.myapplication.ui.navigation.Screens
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val tagUseCases: TagUseCases,
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private var getCategoryJob: Job? = null
    private var getTagJob: Job? = null
    private var getProductJob: Job? = null
    private var getProductInCartJob: Job? = null

    private val _uiState = MutableStateFlow(CatalogState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getCategoriesFromLocal()
        getTagsFromLocal()
        getProductsInCart()
    }

    fun onEvent(event: CatalogEvent) {
        when(event) {
            is CatalogEvent.FetchData -> {
                if (!event.internetAvailable) {
                    sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.noIntenerConnection)))
                    return
                }
                getCategoriesFromApi()
            }

            is CatalogEvent.OnProductItemClick -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.CardProductScreen.route + "/${event.id}"))
            }

            is CatalogEvent.AddProductToBasket -> {
                addProductToCart(event.id)
            }

            is CatalogEvent.RemoveProductToBasket -> {
                removeProductFromCart(event.id)
            }

            is CatalogEvent.ChangeChosenCategory -> {
                changeChosenCategory(event.index)
            }

            is CatalogEvent.IsShowBottomSheet -> {
                isShowBottomSheet(event.isShow)
            }

            is CatalogEvent.OnTagSelection -> {
                onTagSelection(event.index)
            }

            is CatalogEvent.OnBottomSheetReadyBtn -> {
                onBottomSheetReadyBtnClick()
            }

            is CatalogEvent.OnSearchUi -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.SearchScreen.route))
            }

            is CatalogEvent.OnCartClick -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.CartScreen.route))
            }
        }
    }

    /** ProductInCart --------------------------------------------------------------------------! */
    private fun addProductToCart(id: Int) = viewModelScope.launch {
        uiState.value.filteredProducts.addProductToCart(id).let {
            if (it != null) {
                productUseCases.addProduct(it)
            }
        }
    }

    private fun removeProductFromCart(id: Int) = viewModelScope.launch {
        uiState.value.filteredProducts.removeProductFromCart(id).let {
            if (it != null) {
                productUseCases.addProduct(it)
            }
        }
    }

    /** ChangesChosenCategoryIndex ------------------------------------------------------------! **/
    private fun changeChosenCategory(index: Int) = viewModelScope.launch{
        _uiState.update { state ->
            state.copy(
                selectedCategoryIndex = index,
                categories = uiState.value.categories.changeChosenCategory(index)
            )
        }
        // SetsCategoryId Then requestsFilteredProducts
        setCategoryIdToFilter().join()
        getFilteredProducts()
    }

    private fun setCategoryIdToFilter() = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                filterProduct = uiState.value.filterProduct.copy(
                    byCategoryId = uiState.value.categories.getChosenCategoryId()
                )
            )
        }
    }

    /** BottomSheetActions --------------------------------------------------------------------! **/
    private fun isShowBottomSheet(isShow: Boolean) {
        _uiState.update { state ->
            state.copy(
                isBottomSheetVisible = isShow
            )
        }
    }

    private fun onTagSelection(index: Int) = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                tags = uiState.value.tags.onTagSelection(index)
            )
        }
    }

    private fun onBottomSheetReadyBtnClick() = viewModelScope.launch {
        _uiState.update { state ->
            state.copy(
                filterProduct = uiState.value.filterProduct.copy(
                    byTags = uiState.value.tags.getSelectedTags()
                )
            )
        }
        getFilteredProducts()
    }

    /** GetDataFromApi ------------------------------------------------------------------------! **/
    private fun getCategoriesFromApi() = viewModelScope.launch {
        if (uiState.value.status == Status.LOADING) {
            return@launch
        }
        changeStatusLoading(Status.LOADING)
        categoryUseCases.getCategoriesFromApi().let { response ->
            if (response?.isSuccessful != true) {
                changeStatusLoading(Status.ERROR)
                sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error)))
                return@launch
            }
            getTagsFromApi()
        }
    }

    private fun getTagsFromApi() = viewModelScope.launch {
        tagUseCases.getTagsFromApi().let { response ->
            if (response?.isSuccessful != true) {
                changeStatusLoading(Status.ERROR)
                sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error)))
                return@launch
            }

            getProductsFromApi()
        }
    }

    private fun getProductsFromApi() = viewModelScope.launch {
        productUseCases.getProductsFromApi().let { response ->
            if (response?.isSuccessful != true) {
                changeStatusLoading(Status.ERROR)
                sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error)))
                return@launch
            }
            changeStatusLoading(Status.SUCCESS)
        }
    }

    /** GetDataFromLocal ----------------------------------------------------------------------! **/
    private fun getCategoriesFromLocal() {
        getCategoryJob?.cancel()
        getCategoryJob = categoryUseCases.getCategoriesFromLocal().onEach {
            _uiState.update { state ->
                state.copy(
                    categories = it.changeChosenCategory(0),
                    filterProduct = uiState.value.filterProduct.copy(
                        byCategoryId = it.getChosenCategoryIdByIndex(uiState.value.selectedCategoryIndex)
                    )
                )
            }
            getFilteredProducts()
        }.launchIn(viewModelScope)
    }

    private fun getTagsFromLocal() {
        getTagJob?.cancel()
        getTagJob = tagUseCases.getTagsFromLocal().onEach {
            _uiState.update { state ->
                state.copy(
                    tags = it
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getFilteredProducts(filterProduct: FilterProduct = uiState.value.filterProduct) {
        getProductJob?.cancel()
        getProductJob = productUseCases.getProductsFromLocal(filterProduct).onEach {
            _uiState.update { state ->
                state.copy(
                    filteredProducts = it
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun getProductsInCart() {
        val filterProduct = FilterProduct(
            inCart = PRODUCT_IN_CART
        )
        getProductInCartJob?.cancel()
        getProductInCartJob = productUseCases.getProductsFromLocal(filterProduct).onEach {
            if (it.isNotEmpty() && uiState.value.status == Status.None) changeStatusLoading(Status.SUCCESS)
            _uiState.update { state ->
                state.copy(
                    productsInCart = it,
                    totalValueOfCart = it.calculateValueOfProductsInCart()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun changeStatusLoading(status: Status) {
        _uiState.update { state ->
            state.copy(
                status = status
            )
        }
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}