package com.example.networking

import androidx.lifecycle.MutableLiveData

interface NewsRemoteDataSource {
    val remoteNewsLiveData: MutableLiveData<Result<String>>

    suspend fun fetchNews()
}
