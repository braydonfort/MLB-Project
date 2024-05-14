package com.example.domain

import androidx.lifecycle.MutableLiveData
import com.example.networking.NewsResponse

interface NewsFeedRepository {
    val newsLiveData: MutableLiveData<Result<String>>
    suspend fun refreshNewsArticles()
    fun parseJson(jsonString: String): NewsResponse
}