package com.example.domain

import com.example.database.ArticlesDao
import com.example.database.ArticlesEntity
import com.example.domain.mappings.toArticle
import com.example.domain.repo.NewsFeedRepositoryImpl
import com.example.models.Api
import com.example.models.Article
import com.example.models.Links
import com.example.models.Mobile
import com.example.models.News
import com.example.models.Self
import com.example.models.Web
import com.example.networking.NewsRemoteDataSource
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class NewsFeedRepositoryTests {

    private lateinit var newsFeedRepositoryImpl: NewsFeedRepositoryImpl
    @RelaxedMockK
    private lateinit var dataSource: NewsRemoteDataSource
    @RelaxedMockK
    private lateinit var dao: ArticlesDao
    val articleEntity = ArticlesEntity("1","des","type",
        false, Links(Api(News(""), Self("")), Web(""), Mobile("")),
        listOf(),"headline", listOf(),"pub","lm","byline"
    )

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        newsFeedRepositoryImpl = NewsFeedRepositoryImpl(dataSource, dao)
    }

    @After
    fun teardown(){
        unmockkAll()
    }

    @Test
    fun testGetCachedList_success() = runTest{
        val articleEntityList = listOf(articleEntity)
        coEvery { dao.getCachedArticles() } returns articleEntityList
        val actual = newsFeedRepositoryImpl.getCachedArticles()
        val expected = listOf(articleEntity.toArticle())
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun testGetCachedList_fail() = runTest{
        val articleEntityList = listOf<ArticlesEntity>()
        coEvery { dao.getCachedArticles() } returns listOf<ArticlesEntity>()
        val actual = newsFeedRepositoryImpl.getCachedArticles()
        val expected = emptyList<Article>()
        assertThat(actual).isEqualTo(expected)
    }
}