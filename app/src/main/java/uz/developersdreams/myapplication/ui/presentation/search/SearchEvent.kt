package uz.developersdreams.myapplication.ui.presentation.search

sealed class SearchEvent {

    data class OnSearchText(
        val text: String
    ) : SearchEvent()

    data class OnProductItemClick(
        val id: Int
    ) : SearchEvent()

    data class AddProductToBasket(
        val id: Int
    ) : SearchEvent()

    data class RemoveProductToBasket(
        val id: Int
    ) : SearchEvent()

    data object OnPopBack: SearchEvent()

    data object OnClear: SearchEvent()
}