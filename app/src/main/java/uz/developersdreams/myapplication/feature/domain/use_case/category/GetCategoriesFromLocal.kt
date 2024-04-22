package uz.developersdreams.myapplication.feature.domain.use_case.category

import kotlinx.coroutines.flow.Flow
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.repository.CategoryRepository

class GetCategoriesFromLocal(
    private val repository: CategoryRepository
) {

    operator fun invoke() : Flow<List<CategoryItem>> {
        return repository.getCategoriesFromLocal()
    }
}