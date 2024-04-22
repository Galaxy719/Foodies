package uz.developersdreams.myapplication.ui.presentation.search

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
import uz.developersdreams.myapplication.core.extensions.addProductToCart
import uz.developersdreams.myapplication.core.extensions.removeProductFromCart
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.feature.domain.use_case.product.ProductUseCases
import uz.developersdreams.myapplication.ui.navigation.Screens
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private var searchProduct: Job? = null

    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.OnSearchText -> {
                onSearchText(event.text)
            }

            is SearchEvent.OnProductItemClick -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.CardProductScreen.route + "/${event.id}"))
            }

            is SearchEvent.AddProductToBasket -> {
                addProductToCart(event.id)
            }

            is SearchEvent.RemoveProductToBasket -> {
                removeProductFromCart(event.id)
            }

            is SearchEvent.OnPopBack -> {
                sendUiEvent(UiEvent.OnPopBack)
            }

            is SearchEvent.OnClear -> {
                onClearSearch()
            }
        }
    }

    private fun onSearchText(text: String) {
        _uiState.update { state ->
            state.copy(
                filterProduct = uiState.value.filterProduct.copy(
                    byName = text
                )
            )
        }
        searchProduct()
    }

    private fun onClearSearch() {
        _uiState.update { state ->
            state.copy(
                filterProduct = uiState.value.filterProduct.copy(
                    byName = null
                )
            )
        }
        searchProduct()
    }

    private fun searchProduct() = viewModelScope.launch {
        searchProduct?.cancel()
        searchProduct = productUseCases.getProductsFromLocal(uiState.value.filterProduct).onEach {
            _uiState.update { state ->
                state.copy(
                    searchedProducts = if (uiState.value.filterProduct.byName.isNullOrBlank()) emptyList() else it
                )
            }
        }.launchIn(viewModelScope)
    }

    /** ProductInCart --------------------------------------------------------------------------! */
    private fun addProductToCart(id: Int) = viewModelScope.launch {
        uiState.value.searchedProducts.addProductToCart(id).let {
            if (it != null) {
                productUseCases.addProduct(it)
                searchProduct()
            }
        }
    }

    private fun removeProductFromCart(id: Int) = viewModelScope.launch {
        uiState.value.searchedProducts.removeProductFromCart(id).let {
            if (it != null) {
                productUseCases.addProduct(it)
                searchProduct()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}