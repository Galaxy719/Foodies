package uz.developersdreams.myapplication.feature.data.repository_impl

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.developersdreams.myapplication.core.filter.FilterProduct
import uz.developersdreams.myapplication.feature.data.data_source.local.ProductDao
import uz.developersdreams.myapplication.feature.data.data_source.remote.ApiHelper
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val productDao: ProductDao,
) : ProductRepository {
    override suspend fun getProductsFromApi(): Response<List<ProductItem>> {
        return apiHelper.getProducts()
    }

    override suspend fun getProductById(id: Int): ProductItem? {
        return productDao.getProductById(id)
    }

    override fun getProductsFromLocal(filterProduct: FilterProduct): Flow<List<ProductItem>> {
        return productDao.getProducts(
            categoryId = filterProduct.byCategoryId,
            name = filterProduct.byName,
            inCart = filterProduct.inCart
        )
    }

    override suspend fun insertProducts(list: List<ProductItem>) {
        productDao.insertProducts(list)
    }

    override suspend fun deleteProducts() {
        productDao.deleteProducts()
    }
}