package com.example.database.typeconverters

import androidx.room.TypeConverter
import com.example.models.Category
import com.example.models.Links
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room type converter for Link in Articles Entity
 */
class LinksTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromLinks(links: Links?): String? {
        return gson.toJson(links)
    }

    @TypeConverter
    fun toLinks(json: String?): Links? {
        return gson.fromJson(json, object : TypeToken<Links>() {}.type)
    }
}