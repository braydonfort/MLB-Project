package com.example.di

import android.content.Context
import com.example.networking.KtorNewsRemoteDataSource
import com.example.networking.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDatasourceModule {


    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSkipNetworkCall(): Boolean {
        return false
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(client: HttpClient, context: Context, skipNetworkCall: Boolean): NewsRemoteDataSource {
        return KtorNewsRemoteDataSource(client, context, skipNetworkCall)
    }
}
