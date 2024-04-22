package uz.developersdreams.myapplication.ui.presentation.catalog

import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.feature.data.data_source.remote.status.Status
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem

data class CatalogState(
    val status: Status = Status.None,
    val selectedCategoryIndex: Int = 0,
    val filterProduct: FilterProduct = FilterProduct(),
    val categories: List<CategoryItem> = emptyList(),
    val tags: List<TagItem> = emptyList(),
    val filteredProducts: List<ProductItem> = emptyList(),
    val productsInCart: List<ProductItem> = emptyList(),
    val totalValueOfCart: Int = 0,
    val isBottomSheetVisible: Boolean = false,
)