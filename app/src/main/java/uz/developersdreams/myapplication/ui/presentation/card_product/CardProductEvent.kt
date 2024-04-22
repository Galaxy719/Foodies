package uz.developersdreams.myapplication.ui.presentation.card_product

sealed class CardProductEvent {

    data object OnPopBack: CardProductEvent()

    data class OnGetProduct(val id: Int) : CardProductEvent()

    data object OnAddToCart: CardProductEvent()
}