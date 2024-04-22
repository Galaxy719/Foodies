package uz.developersdreams.myapplication.feature.domain.use_case.tag

data class TagUseCases(
    val getTagsFromApi: GetTagsFromApi,
    val getTagsFromLocal: GetTagsFromLocal
)
