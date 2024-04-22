package uz.developersdreams.myapplication.feature.domain.model.tag

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import uz.developersdreams.myapplication.core.util.Constants.TAG_TABLE

@Entity(tableName = TAG_TABLE)
data class TagItem(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_selected")
    val isSelected: Boolean = false
)