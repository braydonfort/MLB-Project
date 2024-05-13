package com.mlb.news.playground.newsfeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlb.news.playground.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsFeedFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArticleAdapter: NewsArticleAdapter
    private lateinit var newsFeedRepository: NewsFeedRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_feed, container, false)
        recyclerView = view.findViewById(R.id.rv_news_feed)
        newsFeedRepository = NewsFeedRepository(requireContext())

        newsArticleAdapter = NewsArticleAdapter(emptyList())
        recyclerView.adapter = newsArticleAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        GlobalScope.launch { newsFeedRepository.refreshNewsArticles() }

        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        newsFeedRepository.newsLiveData.observe(
            viewLifecycleOwner,
            { articles ->
                Log.d("NewsFeedFragment", "articles retrieved")
                if (articles.isFailure) {
                    Toast.makeText(
                        requireContext(),
                        articles.exceptionOrNull()!!.message.toString(),
                        Toast.LENGTH_LONG,
                    ).show()
                } else {
                    val data = newsFeedRepository.parseJson(articles.getOrThrow())
                    newsArticleAdapter.updateData(data.articles)
                }
            },
        )
    }
}
