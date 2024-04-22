package uz.developersdreams.myapplication.feature.data.data_source.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataConverter {

    @TypeConverter
    fun listToJson(value: List<Int>?): String? = Gson().toJson(value)

    @TypeConverter
    fun fromJson(value: String?): List<Int> {
        val listType = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}