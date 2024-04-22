package uz.developersdreams.myapplication.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem

interface ProductRepository {

    suspend fun getProductsFromApi() : Response<List<ProductItem>>

    suspend fun getProductById(id: Int): ProductItem?

    fun getProductsFromLocal(filterProduct: FilterProduct = FilterProduct()) : Flow<List<ProductItem>>

    suspend fun insertProducts(list: List<ProductItem>)

    suspend fun deleteProducts()
}