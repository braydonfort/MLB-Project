package com.example.domain

import androidx.lifecycle.MutableLiveData
import com.example.database.ArticlesDao
import com.example.database.ArticlesEntity
import com.example.domain.repo.NewsFeedRepositoryImpl
import com.example.networking.NewsRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsFeedRepoImplTests {

    lateinit var newsFeedRepositoryImpl: NewsFeedRepositoryImpl
    @RelaxedMockK
    lateinit var newsRemoteDataSource: NewsRemoteDataSource
    @RelaxedMockK
    lateinit var articlesDao: ArticlesDao
    @RelaxedMockK
    lateinit var articleList: List<ArticlesEntity>

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        newsFeedRepositoryImpl = NewsFeedRepositoryImpl(newsRemoteDataSource, articlesDao)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
    @Test
    fun `refreshNewsArticles should post success result when news fetch successful`() = runTest {
        // Mock data and behavior
        val successResult = Result.success("Test news data")
        coEvery { newsRemoteDataSource.fetchNews() } just Runs
        every { newsRemoteDataSource.remoteNewsLiveData } returns MutableLiveData(successResult)

        // Trigger the function
        newsFeedRepositoryImpl.refreshNewsArticles()

        // Verify behavior
        coVerify { newsRemoteDataSource.fetchNews() }
        verify { newsRemoteDataSource.remoteNewsLiveData }
        verify { newsRemoteDataSource.remoteNewsLiveData.observeForever(any()) }

        // Verify LiveData behavior
        assert(newsFeedRepositoryImpl.newsLiveData.value == successResult)
    }

    @Test
    fun `refreshNewsArticles should post failure result when news fetch fails`() = runTest {
        // Mock data and behavior
        val failureResult = Result.failure<String>(RuntimeException("Test error"))
        coEvery { newsRemoteDataSource.fetchNews() } just Runs
        every { newsRemoteDataSource.remoteNewsLiveData } returns MutableLiveData(failureResult)

        // Trigger the function
        newsFeedRepositoryImpl.refreshNewsArticles()

        // Verify behavior
        coVerify { newsRemoteDataSource.fetchNews() }
        verify { newsRemoteDataSource.remoteNewsLiveData }
        verify { newsRemoteDataSource.remoteNewsLiveData.observeForever(any()) }

        // Verify LiveData behavior
        assert(newsFeedRepositoryImpl.newsLiveData.value == failureResult)
    }

}