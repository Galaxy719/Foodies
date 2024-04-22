package uz.developersdreams.myapplication.core.filter

data class FilterProduct(
    val byCategoryId: Int? = null,
    val byName: String? = null,
    val byTags: List<Int> = emptyList(),
    val bySale: Boolean = false,
    val inCart: Int? = null
)
