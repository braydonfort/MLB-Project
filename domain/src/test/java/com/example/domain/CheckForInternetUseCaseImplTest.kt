package com.example.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import com.example.domain.usecase.CheckForInternetUseCaseImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class CheckForInternetUseCaseImplTest {

    lateinit var checkForInternetUseCaseImpl: CheckForInternetUseCaseImpl
    @RelaxedMockK
    lateinit var context: Context
    @RelaxedMockK
    lateinit var connectivityManager: ConnectivityManager
    @RelaxedMockK
    lateinit var network: Network

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        checkForInternetUseCaseImpl = CheckForInternetUseCaseImpl()
    }
    @After
    fun tearDown(){
        unmockkAll()
    }

    @Test
    fun testForInterNetConnections_success() = runTest{
        coEvery { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager } returns connectivityManager
        coEvery { connectivityManager.activeNetwork } returns network
        coEvery { connectivityManager.getNetworkCapabilities(network) }
        coEvery { connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        coEvery { connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true
        coEvery { connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns true
        val usecase = checkForInternetUseCaseImpl.execute(context)
        assertThat(usecase.first()).isEqualTo(true)
    }
    @Test
    fun testForInterNetConnections_failure() = runTest{
        coEvery { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager } returns connectivityManager
        coEvery { connectivityManager.activeNetwork } returns network
        coEvery { connectivityManager.getNetworkCapabilities(network) }
        coEvery { connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns false
        coEvery { connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns false
        coEvery { connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) } returns false
        val usecase = checkForInternetUseCaseImpl.execute(context)
        assertThat(usecase.first()).isEqualTo(false)
    }

}