package com.example.domain.usecase

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * CheckForInternetUseCaseImpl
 * Implementation class to check for internet connection
 * @see[CheckForInternetUseCase]
 * execute has @param[Context]
 */
class CheckForInternetUseCaseImpl @Inject constructor(): CheckForInternetUseCase {
    override suspend fun execute(context: Context): Flow<Boolean> = flow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        if (capabilities != null)
        {
            emit(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            emit(false)
        }
    }
}
