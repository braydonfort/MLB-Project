package com.example.networking

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {
    val remoteNewsLiveData: MutableLiveData<Result<String>>

    suspend fun fetchNews()
}
