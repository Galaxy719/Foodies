package uz.developersdreams.myapplication.feature.domain.use_case.tag

import kotlinx.coroutines.flow.Flow
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem
import uz.developersdreams.myapplication.feature.domain.repository.TagRepository

class GetTagsFromLocal(
    private val repository: TagRepository
) {

    operator fun invoke() : Flow<List<TagItem>> {
        return repository.getTagsFromLocal()
    }
}