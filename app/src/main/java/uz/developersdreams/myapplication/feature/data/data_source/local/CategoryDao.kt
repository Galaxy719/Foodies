package uz.developersdreams.myapplication.feature.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryItem>)

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): Flow<List<CategoryItem>>

    @Query("DELETE FROM category_table")
    suspend fun deleteCategories()
}