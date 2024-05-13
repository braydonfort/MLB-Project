package com.mlb.news.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mlb.news.playground.newsfeed.NewsFeedFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Load the NewsFeedFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NewsFeedFragment())
                .commit()
        }
    }
}
