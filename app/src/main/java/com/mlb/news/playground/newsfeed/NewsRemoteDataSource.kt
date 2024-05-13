package com.mlb.news.playground.newsfeed

import androidx.lifecycle.MutableLiveData

interface NewsRemoteDataSource {
    val remoteNewsLiveData: MutableLiveData<Result<String>>

    suspend fun fetchNews()
}
