package com.mlb.news.playground.newsfeed

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.mlb.news.playground.R

class NewsArticleAdapter(private var articles: List<com.example.networking.Article>) :
    RecyclerView.Adapter<NewsArticleAdapter.ArticleViewHolder>() {
    fun updateData(newArticles: List<com.example.networking.Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_article_image)
        val headlineTextView: TextView = view.findViewById(R.id.tv_article_headline)
        val descriptionTextView: TextView = view.findViewById(R.id.tv_article_description)
        val card: MaterialCardView = view.findViewById(R.id.cv_article)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_news_article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
    ) {
        val article = articles[position]
        holder.headlineTextView.text = article.headline
        holder.descriptionTextView.text = article.description

        holder.card.setOnClickListener {
            val url = article.links?.mobile?.href ?: "http://www.mlb.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent)
        }
        holder.imageView.load(article.images.firstOrNull()?.url)
    }

    override fun getItemCount() = articles.size
}
