package uz.developersdreams.myapplication.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTags(tags: List<TagItem>)

    @Query("SELECT * FROM tag_table")
    fun getAllTags(): Flow<List<TagItem>>

    @Query("DELETE FROM tag_table")
    suspend fun deleteTags()
}