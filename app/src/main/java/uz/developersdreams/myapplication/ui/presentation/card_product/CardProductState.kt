package uz.developersdreams.myapplication.ui.presentation.card_product

import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem

data class CardProductState(
    val productItem: ProductItem? = null,
    var alreadyAddedToCart: Boolean = false
)