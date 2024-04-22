package uz.developersdreams.myapplication.feature.domain.use_case.product

import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository

class GetProductById(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(id: Int) : ProductItem? {
        return repository.getProductById(id)
    }
}