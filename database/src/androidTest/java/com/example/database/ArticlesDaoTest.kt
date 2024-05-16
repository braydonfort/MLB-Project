package com.example.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.models.Api
import com.example.models.Category
import com.example.models.Image
import com.example.models.Links
import com.example.models.Mobile
import com.example.models.News
import com.example.models.Self
import com.example.models.Web
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ArticlesDaoTest {

    private lateinit var database: MlbNewsPlaygroundDatabase
    private lateinit var articlesDao: ArticlesDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MlbNewsPlaygroundDatabase::class.java
        ).allowMainThreadQueries().build()

        articlesDao = database.articlesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieveArticles() = runTest {
        val links: Links = Links(Api(News(""), Self("")), Web(""), Mobile(""))
        val categories: List<Category> = emptyList()
        val images: List<Image> = emptyList()
        val articles = listOf(
            ArticlesEntity("1", "Title 1", "type",false,links,categories,"headline1",
                images,"published","modified","byline"),
            ArticlesEntity("2", "Title 2", "type",false,links,categories,"headline2",
                images,"published","modified","byline")
        )

        // Insert articles into the database
        articlesDao.insertArticle(articles)

        // Retrieve articles from the database
        val cachedArticles = articlesDao.getCachedArticles()

        // Verify that the retrieved articles match the inserted ones
        assert(cachedArticles.size == articles.size)
        assert(cachedArticles.containsAll(articles))
    }
}