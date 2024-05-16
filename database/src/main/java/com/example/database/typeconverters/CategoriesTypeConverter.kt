package com.example.database.typeconverters

import androidx.room.TypeConverter
import com.example.models.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room type converter for List<Catergory> in Articles Entity
 */
class CategoriesTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromCategoryList(categories: List<Category>?): String? {
        return gson.toJson(categories)
    }

    @TypeConverter
    fun toCategoryList(json: String?): List<Category>? {
        return gson.fromJson(json, object : TypeToken<List<Category>>() {}.type)
    }
}