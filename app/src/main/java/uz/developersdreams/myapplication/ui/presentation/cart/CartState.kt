package uz.developersdreams.myapplication.ui.presentation.cart

import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem

data class CartState(
    val productsInCart: List<ProductItem> = emptyList(),
    val totalValueOfCart: Int = 0,
)
