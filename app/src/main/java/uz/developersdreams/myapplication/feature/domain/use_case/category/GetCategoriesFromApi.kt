package uz.developersdreams.myapplication.feature.domain.use_case.category

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.repository.CategoryRepository

class GetCategoriesFromApi(
    private val repository: CategoryRepository
) {

    suspend operator fun invoke() : Response<List<CategoryItem>>? {
        return try {
            val response = repository.getCategoriesFromApi()
            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    launch { repository.deleteCategories() }.join()
                    launch { repository.insertCategories(response.body()!!) }
                }
            }
            response
        } catch (_ : Exception) {
            null
        }
    }
}