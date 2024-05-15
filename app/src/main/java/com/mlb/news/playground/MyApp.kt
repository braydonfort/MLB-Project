package com.mlb.news.playground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
//    private val uiModule = module {
//       viewModel{NewsFeedViewModel(get())}
//    }
    override fun onCreate() {
        super.onCreate()
//        startKoin{
//            androidContext(this@MyApp)
//            modules(uiModule, appModule)
//        }
    }
}