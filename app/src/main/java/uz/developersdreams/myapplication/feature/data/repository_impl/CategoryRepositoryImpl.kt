package uz.developersdreams.myapplication.feature.data.repository_impl

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.developersdreams.myapplication.feature.data.data_source.local.CategoryDao
import uz.developersdreams.myapplication.feature.data.data_source.remote.ApiHelper
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun getCategoriesFromApi(): Response<List<CategoryItem>> {
        return apiHelper.getCategories()
    }

    override fun getCategoriesFromLocal(): Flow<List<CategoryItem>> {
        return categoryDao.getAllCategories()
    }

    override suspend fun insertCategories(list: List<CategoryItem>) {
        categoryDao.insertCategories(list)
    }

    override suspend fun deleteCategories() {
        categoryDao.deleteCategories()
    }
}