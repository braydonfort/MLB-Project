package com.example.di

import com.example.domain.repo.NewsFeedRepository
import com.example.domain.repo.NewsFeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun provideRepo(newsFeedRepositoryImpl: NewsFeedRepositoryImpl): NewsFeedRepository {
        return newsFeedRepositoryImpl
    }
}