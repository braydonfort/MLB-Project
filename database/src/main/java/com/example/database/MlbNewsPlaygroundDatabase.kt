package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.typeconverters.CategoriesTypeConverter
import com.example.database.typeconverters.ImagesTypeConverter
import com.example.database.typeconverters.LinksTypeConverter

@Database(entities = [ArticlesEntity::class], version = 1)
@TypeConverters(CategoriesTypeConverter::class,ImagesTypeConverter::class,LinksTypeConverter::class)
abstract class MlbNewsPlaygroundDatabase: RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}