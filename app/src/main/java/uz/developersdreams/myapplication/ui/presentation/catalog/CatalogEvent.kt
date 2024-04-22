package uz.developersdreams.myapplication.ui.presentation.catalog

sealed class CatalogEvent {

    data class FetchData(
        val internetAvailable: Boolean
    ) : CatalogEvent()

    data class OnProductItemClick(
        val id: Int
    ) : CatalogEvent()

    data class AddProductToBasket(
        val id: Int
    ) : CatalogEvent()

    data class RemoveProductToBasket(
        val id: Int
    ) : CatalogEvent()

    data class ChangeChosenCategory(
        val index: Int
    ) : CatalogEvent()

    data class IsShowBottomSheet(
        val isShow: Boolean
    ) : CatalogEvent()

    data class OnTagSelection(
        val index: Int
    ) : CatalogEvent()

    data object OnBottomSheetReadyBtn: CatalogEvent()

    data object OnSearchUi: CatalogEvent()

    data object OnCartClick: CatalogEvent()
}