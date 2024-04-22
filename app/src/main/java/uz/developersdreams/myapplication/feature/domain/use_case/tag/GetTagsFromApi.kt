package uz.developersdreams.myapplication.feature.domain.use_case.tag

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem
import uz.developersdreams.myapplication.feature.domain.repository.TagRepository

class GetTagsFromApi(
    private val repository: TagRepository
) {

    suspend operator fun invoke() : Response<List<TagItem>>? {
        return try {
            val response = repository.getTagsFromApi()
            if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                CoroutineScope(Dispatchers.IO).launch {
                    launch { repository.deleteTags() }.join()
                    launch { repository.insertTags(response.body()!!) }
                }
            }
            response
        } catch (_ : Exception) {
            null
        }
    }
}