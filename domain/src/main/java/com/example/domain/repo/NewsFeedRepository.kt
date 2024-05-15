package com.example.domain.repo

import androidx.lifecycle.MutableLiveData
import com.example.networking.NewsResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface NewsFeedRepository {
    val newsLiveData: MutableLiveData<Result<String>>
    suspend fun refreshNewsArticles()
    fun parseJson(jsonString: String): NewsResponse
}