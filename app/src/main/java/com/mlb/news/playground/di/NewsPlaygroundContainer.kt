package com.mlb.news.playground.di

import android.content.Context
import com.mlb.news.playground.newsfeed.KtorNewsRemoteDataSource
import com.mlb.news.playground.newsfeed.NewsRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

object NewsPlaygroundContainer {
    fun provideNewsRemoteDataSource(context: Context): NewsRemoteDataSource {
        return KtorNewsRemoteDataSource(
            client = provideKtorClient(),
            context = context,
            skipNetworkCall = false,
        )
    }

    private fun provideKtorClient(): HttpClient {
        return HttpClient(Android) { install(JsonFeature) { serializer = KotlinxSerializer() } }
    }
}
