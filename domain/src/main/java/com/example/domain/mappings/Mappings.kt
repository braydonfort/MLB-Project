package com.example.domain.mappings

import com.example.database.ArticlesEntity
import com.example.models.Article

fun ArticlesEntity.toArticle(): Article{
    return Article(dataSourceIdentifier, description, type, premium, links, categories, headline, images, published, lastModified, byline)
}
fun Article.toArticlesEntity(): ArticlesEntity{
    return ArticlesEntity(dataSourceIdentifier, description, type, premium, links, categories, headline, images, published, lastModified, byline)
}