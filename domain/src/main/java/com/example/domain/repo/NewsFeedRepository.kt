package com.example.domain.repo

import androidx.lifecycle.MutableLiveData
import com.example.models.Article
import com.example.models.NewsResponse
import kotlinx.coroutines.flow.Flow

/**
 * NewsFeedRepository
 * Interface for the NewsFeedRepository
 * @see[NewsFeedRepositoryImpl]
 */
interface NewsFeedRepository {
    val newsLiveData: MutableLiveData<Result<String>>
    suspend fun refreshNewsArticles()
    fun parseJson(jsonString: String): NewsResponse

    suspend fun getCachedArticles(): List<Article>

}