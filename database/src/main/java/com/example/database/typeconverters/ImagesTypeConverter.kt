package com.example.database.typeconverters

import androidx.room.TypeConverter
import com.example.models.Category
import com.example.models.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room type converter for List<Image> in Articles Entity
 */
class ImagesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromImageList(images: List<Image>?): String? {
        return gson.toJson(images)
    }

    @TypeConverter
    fun toImageList(json: String?): List<Image>? {
        return gson.fromJson(json, object : TypeToken<List<Image>>() {}.type)
    }
}