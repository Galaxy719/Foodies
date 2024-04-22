package uz.developersdreams.myapplication.feature.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.developersdreams.myapplication.feature.domain.model.category.CategoryItem
import uz.developersdreams.myapplication.feature.domain.model.product.ProductItem
import uz.developersdreams.myapplication.feature.domain.model.tag.TagItem

@Database(
    entities = [CategoryItem::class, TagItem::class, ProductItem::class],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val categoryDao: CategoryDao
    abstract val tagDao: TagDao
    abstract val productDao: ProductDao
}