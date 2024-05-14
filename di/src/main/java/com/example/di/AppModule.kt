package com.example.di

import com.example.domain.NewsFeedRepository
import com.example.domain.NewsFeedRepositoryImpl
import com.example.networking.KtorNewsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) { install(JsonFeature) { serializer = KotlinxSerializer() } }
    }
    single {
        KtorNewsRemoteDataSource(get(),get(),get())
    }
    single<NewsFeedRepository> {
        NewsFeedRepositoryImpl(get(),get())
    }

}