package uz.developersdreams.myapplication.feature.data.data_source.remote

import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun getCategories(): Response<List<CategoryItem>> {
        return apiService.getCategories()
    }

    override suspend fun getTags(): Response<List<TagItem>> {
        return apiService.getTags()
    }

    override suspend fun getProducts(): Response<List<ProductItem>> {
        return apiService.getProducts()
    }
}