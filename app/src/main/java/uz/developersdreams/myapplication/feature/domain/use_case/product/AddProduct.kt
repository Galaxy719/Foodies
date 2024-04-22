package uz.developersdreams.myapplication.feature.domain.use_case.product

import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository

class AddProduct(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(productItem: ProductItem) {
        repository.insertProducts(listOf(productItem))
    }
}