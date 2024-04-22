package uz.developersdreams.myapplication.feature.domain.model.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.developersdreams.myapplication.core.util.Constants.CATEGORY_TABLE

@Entity(tableName = CATEGORY_TABLE)
data class CategoryItem(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_selected")
    var isSelected: Boolean = false
)