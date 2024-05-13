package com.mlb.news.playground.newsfeed

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KtorNewsRemoteDataSource(
    private val client: HttpClient,
    private val context: Context,
    private val skipNetworkCall: Boolean, // If true, will load mock data from assets instead of making an API call to fetch the data.
) : NewsRemoteDataSource {
    override val remoteNewsLiveData = MutableLiveData<Result<String>>()

    override suspend fun fetchNews() {
        withContext(Dispatchers.IO) {
            runCatching {
                if (skipNetworkCall) {
                    loadJsonFromAssets()
                } else {
                    client.get<String> {
                        url("https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/news")
                    }
                }
            }
                .onSuccess {
                    Log.d("KtorNewsRemoteDataSource", "news fetch successful")
                    remoteNewsLiveData.postValue(Result.success(it))
                }
                .onFailure {
                    Log.e("KtorNewsRemoteDataSource", "error: $it")
                    remoteNewsLiveData.postValue(Result.failure(it))
                }
        }
    }

    private fun loadJsonFromAssets(): String {
        return context.assets.open("mock_news_data.json").bufferedReader().use { it.readText() }
    }
}
