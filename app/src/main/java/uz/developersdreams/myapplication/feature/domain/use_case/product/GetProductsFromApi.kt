package uz.developersdreams.myapplication.feature.domain.use_case.product

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.repository.ProductRepository

class GetProductsFromApi(
    private val repository: ProductRepository
) {

    suspend operator fun invoke() : Response<List<ProductItem>>? {
        return try {
            val response = repository.getProductsFromApi()
            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    launch { repository.deleteProducts() }.join()
                    launch { repository.insertProducts(response.body()!!) }
                }
            }
            response
        } catch (_ : Exception) {
            null
        }
    }
}