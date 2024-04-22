package uz.developersdreams.myapplication.feature.domain.use_case.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository

class GetProductsFromLocal(
    private val repository: ProductRepository
) {

    operator fun invoke(filterProduct: FilterProduct) : Flow<List<ProductItem>> {
        return repository.getProductsFromLocal(filterProduct).map { items ->
            items.filterProductByTags(filterProduct)
        }
    }

    private fun List<ProductItem>.filterProductByTags(filterProduct: FilterProduct): List<ProductItem> {
        if (filterProduct.byTags.isEmpty()) return this
        return this.filter { product -> product.tagIds.any { it in filterProduct.byTags } }
    }
}