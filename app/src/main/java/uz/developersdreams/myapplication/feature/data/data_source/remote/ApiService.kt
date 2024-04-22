package uz.developersdreams.myapplication.feature.data.data_source.remote

import retrofit2.Response
import retrofit2.http.GET
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem

interface ApiService {

    @GET("Categories.json")
    suspend fun getCategories() : Response<List<CategoryItem>>

    @GET("Tags.json")
    suspend fun getTags() : Response<List<TagItem>>

    @GET("Products.json")
    suspend fun getProducts() : Response<List<ProductItem>>
}