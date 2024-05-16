package com.example.networking

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

/**
 * NewsRemoteDataSource
 * Interface for network datasource
 * @see[KtorNewsRemoteDataSource]
 */
interface NewsRemoteDataSource {
    val remoteNewsLiveData: MutableLiveData<Result<String>>

    suspend fun fetchNews()
}
