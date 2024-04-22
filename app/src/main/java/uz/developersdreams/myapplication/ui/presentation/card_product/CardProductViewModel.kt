package uz.developersdreams.myapplication.ui.presentation.card_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.developersdreams.myapplication.R
import uz.developersdreams.myapplication.core.util.UiEvent
import uz.developersdreams.myapplication.core.util.UiText
import uz.developersdreams.myapplication.feature.domain.use_case.product.ProductUseCases
import javax.inject.Inject

@HiltViewModel
class CardProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardProductState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CardProductEvent) {
        when(event) {
            is CardProductEvent.OnPopBack -> {
                sendUiEvent(UiEvent.OnPopBack)
            }

            is CardProductEvent.OnGetProduct -> {
                getProductById(event.id)
            }

            is CardProductEvent.OnAddToCart -> {
                addProductToCart()
            }
        }
    }

    private fun getProductById(id: Int) = viewModelScope.launch {
        productUseCases.getProductById(id).let {
            _uiState.update { state ->
                state.copy(
                    productItem = it,
                    alreadyAddedToCart = it?.valueOfInCart!! > 0
                )
            }
        }
    }

    /** ProductInCart --------------------------------------------------------------------------! */
    private fun addProductToCart() = viewModelScope.launch {
        if (uiState.value.alreadyAddedToCart) {
            sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.alreadyAddedToCart)))
            return@launch
        }
        uiState.value.productItem.let {
            val tempProduct = it?.copy(valueOfInCart = it.valueOfInCart + 1)
            if (tempProduct != null) {
                productUseCases.addProduct(tempProduct)
                _uiState.update { state ->
                    state.copy(
                        alreadyAddedToCart = true
                    )
                }
                sendUiEvent(UiEvent.ShowSnackBar(UiText.StringResource(R.string.addedToCart)))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}