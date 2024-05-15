package com.example.domain.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.database.ArticlesDao
import com.example.domain.mappings.toArticle
import com.example.domain.mappings.toArticlesEntity
import com.example.models.Article
import com.example.networking.NewsRemoteDataSource
import com.example.models.NewsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(private val newsRemoteDataSource: NewsRemoteDataSource, private val articlesDao: ArticlesDao):
    NewsFeedRepository {
    override val newsLiveData = MutableLiveData<Result<String>>()

    override suspend fun refreshNewsArticles() {
        Log.d("NewsFeedRepository", "refreshNewsArticles")
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

    override fun parseJson(jsonString: String): NewsResponse {
        val type = object : TypeToken<NewsResponse>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    override suspend fun getCachedArticles(): Flow<List<Article>>? {
        return flow {articlesDao.getCachedArticles().first().toArticle()}
    }

    override suspend fun storeArticles(article: List<Article>) = withContext(Dispatchers.IO) {
        articlesDao.insertArticle(article.map { it.toArticlesEntity() })
    }
}
