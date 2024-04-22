package uz.developersdreams.myapplication.feature.data.data_source.remote

import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem

interface ApiHelper {

    suspend fun getCategories() : Response<List<CategoryItem>>

    suspend fun getTags() : Response<List<TagItem>>

    suspend fun getProducts() : Response<List<ProductItem>>
}