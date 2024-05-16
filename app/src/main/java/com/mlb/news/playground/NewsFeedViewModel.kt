package com.mlb.news.playground


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.repo.NewsFeedRepository
import com.example.domain.usecase.CheckForInternetUseCase
import com.example.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * NewsFeedViewModel
 * View Model For News Feed Page
 *  @param[NewsFeedRepository]
 *  @param[CheckForInternetUseCase]
 */


@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
    private val checkForInternetUseCase: CheckForInternetUseCase): ViewModel() {

    var articleList = MutableStateFlow<List<Article>>(listOf())
    val internetConnection = MutableStateFlow<Boolean>(true)
    private val newsResponse = newsFeedRepository.newsLiveData

    /**
     * Function to update articleList used in the News Feed Page
     * If internet is working get the freshly fetched list
     * If no internet or failed network call try to get cached list if there is one
     */
     suspend fun setList(){
             try {
                 newsFeedRepository.refreshNewsArticles()
                 newsResponse.observeForever {
                     if (newsResponse.value?.isSuccess == true) {
                         val articles = newsFeedRepository.parseJson(it.getOrThrow()).articles
                         viewModelScope.launch(Dispatchers.IO) {
                             articleList.emit(articles)
                         }
                     }
                 }
                 if(articleList.value.isEmpty()) {
                     viewModelScope.launch(Dispatchers.IO) {
                         val cachedArticles = newsFeedRepository.getCachedArticles()
                         if (cachedArticles.isNotEmpty()) {
                             articleList.emit(cachedArticles)
                         }
                     }
                 }
             } catch (e: Exception) {
                Log.e("viewModelError","error: ${e.message}")
             }
     }

    /**
     * Function to check for internet connection
     */
     fun checkForInternet(context: Context){
        viewModelScope.launch {
            internetConnection.emit(checkForInternetUseCase.execute(context).first())
        }
    }



}