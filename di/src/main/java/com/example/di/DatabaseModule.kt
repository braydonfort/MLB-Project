package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.database.ArticlesDao
import com.example.database.MlbNewsPlaygroundDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MlbNewsPlaygroundDatabase {
        return Room.databaseBuilder(
            context,
            MlbNewsPlaygroundDatabase::class.java,
            "mlb-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(database: MlbNewsPlaygroundDatabase): ArticlesDao{
        return database.articlesDao()
    }


}