package uz.developersdreams.myapplication.ui.presentation.search

import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem

data class SearchState(
    val searchedProducts: List<ProductItem> = emptyList(),
    val filterProduct: FilterProduct = FilterProduct()
)