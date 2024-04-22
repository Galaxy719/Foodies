package uz.developersdreams.myapplication.ui.presentation.cart

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
import uz.developersdreams.myapplication.core.extensions.calculateValueOfProductsInCart
import uz.developersdreams.myapplication.core.extensions.removeProductFromCart
import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.core.util.Constants
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.feature.domain.use_case.product.ProductUseCases
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private var getProductInCartJob: Job? = null

    private val _uiState = MutableStateFlow(CartState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getProductsInCart()
    }

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.OnPopBack -> {
                sendUiEvent(UiEvent.OnPopBack)
            }

            is CartEvent.AddProductToBasket -> {
                addProductToCart(event.id)
            }

            is CartEvent.RemoveProductToBasket -> {
                removeProductFromCart(event.id)
            }

            is CartEvent.OnBuyClick -> {

            }
        }
    }

    /** ProductInCart --------------------------------------------------------------------------! */
    private fun addProductToCart(id: Int) = viewModelScope.launch {
        uiState.value.productsInCart.addProductToCart(id).let {
            if (it != null) {
                productUseCases.addProduct(it)
            }
        }
    }

    private fun removeProductFromCart(id: Int) = viewModelScope.launch {
        uiState.value.productsInCart.removeProductFromCart(id).let {
            if (it != null) {
                productUseCases.addProduct(it)
            }
        }
    }

    /** GetDataFromLocal ----------------------------------------------------------------------! **/
    private fun getProductsInCart() {
        val filterProduct = FilterProduct(
            inCart = Constants.PRODUCT_IN_CART
        )
        getProductInCartJob?.cancel()
        getProductInCartJob = productUseCases.getProductsFromLocal(filterProduct).onEach {
            _uiState.update { state ->
                state.copy(
                    productsInCart = it,
                    totalValueOfCart = it.calculateValueOfProductsInCart()
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}