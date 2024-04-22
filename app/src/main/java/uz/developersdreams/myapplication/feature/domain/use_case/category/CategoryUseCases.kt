package uz.developersdreams.myapplication.feature.domain.use_case.category

data class CategoryUseCases(
    val getCategoriesFromApi: GetCategoriesFromApi,
    val getCategoriesFromLocal: GetCategoriesFromLocal
)
