package uz.developersdreams.myapplication.feature.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem

interface TagRepository {

    suspend fun getTagsFromApi() : Response<List<TagItem>>

    fun getTagsFromLocal() : Flow<List<TagItem>>

    suspend fun insertTags(list: List<TagItem>)

    suspend fun deleteTags()
}