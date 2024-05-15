package com.mlb.news.playground


import android.content.Context
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
    private val checkForInternetUseCase: CheckForInternetUseCase): ViewModel() {

    val articleList = MutableStateFlow<List<Article>>(emptyList())
    val internetConnection = MutableStateFlow<Boolean>(true)
    val newsResponse = newsFeedRepository.newsLiveData

     fun setList(){
         viewModelScope.launch {
                 newsFeedRepository.refreshNewsArticles()
                 newsResponse.observeForever { result ->
                     if (result.isFailure) {
                         viewModelScope.launch(Dispatchers.IO) {
                             articleList.value = newsFeedRepository.getCachedArticles()?.first() ?: emptyList()
                         }
                     } else {
                         newsFeedRepository.parseJson(result.getOrThrow()).articles
                     }
                 }
             }
         }

     fun checkForInternet(context: Context){
        viewModelScope.launch {
            internetConnection.value = checkForInternetUseCase.execute(context).first()
        }
    }



}