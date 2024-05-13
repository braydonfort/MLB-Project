package com.mlb.news.playground.newsfeed

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mlb.news.playground.di.NewsPlaygroundContainer
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NewsFeedRepository(private val context: Context) {
    val newsLiveData = MutableLiveData<Result<String>>()

    suspend fun refreshNewsArticles() {
        Log.d("NewsFeedRepository", "refreshNewsArticles")
        // need to figure out caching
        val cache = 1 > 2
        if (!cache) {
            NewsPlaygroundContainer.provideNewsRemoteDataSource(context).apply {
                fetchNews()

                MainScope().launch {
                    remoteNewsLiveData.observeForever { news ->
                        if (news.isFailure) {
                            newsLiveData.postValue(Result.failure(news.exceptionOrNull()!!))
                        } else {
                            Log.d("NewsFeedRepository", "news fetch successful")
                            newsLiveData.postValue(Result.success(news.getOrThrow()))
                        }
                    }
                }
            }
        }
    }

    fun parseJson(jsonString: String): NewsResponse {
        val type = object : TypeToken<NewsResponse>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}
