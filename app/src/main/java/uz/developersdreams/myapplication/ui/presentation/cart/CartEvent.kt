package uz.developersdreams.myapplication.ui.presentation.cart

sealed class CartEvent {

    data object OnPopBack: CartEvent()

    data class AddProductToBasket(
        val id: Int
    ) : CartEvent()

    data class RemoveProductToBasket(
        val id: Int
    ) : CartEvent()

    data object OnBuyClick: CartEvent()
}