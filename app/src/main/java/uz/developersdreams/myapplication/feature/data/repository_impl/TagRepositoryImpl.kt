package uz.developersdreams.myapplication.feature.data.repository_impl

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import uz.developersdreams.myapplication.feature.data.data_source.local.TagDao
import uz.developersdreams.myapplication.feature.data.data_source.remote.ApiHelper
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem
import uz.developersdreams.myapplication.feature.domain.repository.TagRepository

class TagRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val tagDao: TagDao
) : TagRepository {
    override suspend fun getTagsFromApi(): Response<List<TagItem>> {
        return apiHelper.getTags()
    }

    override fun getTagsFromLocal(): Flow<List<TagItem>> {
        return tagDao.getAllTags()
    }

    override suspend fun insertTags(list: List<TagItem>) {
        tagDao.insertTags(list)
    }

    override suspend fun deleteTags() {
        tagDao.deleteTags()
    }
}