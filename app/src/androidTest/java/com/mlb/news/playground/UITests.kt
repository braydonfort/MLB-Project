package com.mlb.news.playground

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.models.Api
import com.example.models.Article
import com.example.models.Category
import com.example.models.Image
import com.example.models.Links
import com.example.models.Mobile
import com.example.models.News
import com.example.models.Self
import com.example.models.Web
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testPageTitle() {
        composeTestRule.onNodeWithTag("MLBTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("MLBTitle").assertTextContains("MLB News Playground")
    }
    @Test
    fun testList(){
        composeTestRule.onNodeWithTag("lazylist").assertExists()
    }

}