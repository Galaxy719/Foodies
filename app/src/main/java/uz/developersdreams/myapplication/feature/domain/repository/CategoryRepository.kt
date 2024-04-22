package uz.developersdreams.myapplication.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem

interface CategoryRepository {

    suspend fun getCategoriesFromApi() : Response<List<CategoryItem>>

    fun getCategoriesFromLocal() : Flow<List<CategoryItem>>

    suspend fun insertCategories(list: List<CategoryItem>)

    suspend fun deleteCategories()
}