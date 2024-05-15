package com.example.domain.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.networking.NewsRemoteDataSource
import com.example.networking.NewsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource):
    NewsFeedRepository {
    override val newsLiveData = MutableLiveData<Result<String>>()

    override suspend fun refreshNewsArticles() {
        Log.d("NewsFeedRepository", "refreshNewsArticles")
        // need to figure out caching
        val cache = 1 > 2
        if (!cache) {
            newsRemoteDataSource.apply {
                fetchNews()

                MainScope().launch {
                    remoteNewsLiveData.observeForever{ news ->
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

    override fun parseJson(jsonString: String): NewsResponse {
        val type = object : TypeToken<NewsResponse>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}
