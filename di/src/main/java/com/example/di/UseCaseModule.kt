package com.example.di

import com.example.domain.usecase.CheckForInternetUseCase
import com.example.domain.usecase.CheckForInternetUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideCheckForInternetUseCase(checkForInternetUseCaseImpl: CheckForInternetUseCaseImpl): CheckForInternetUseCase{
        return checkForInternetUseCaseImpl
    }
}