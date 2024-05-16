package com.example.domain.mappings

import com.example.database.ArticlesEntity
import com.example.models.Article

/**
 * Mappings File for Mapping between data class room entity and data class for network call
 */

fun ArticlesEntity.toArticle(): Article{
    return Article(dataSourceIdentifier, description, type, premium, links, categories, headline, images, published, lastModified, byline)
}
fun Article.toArticlesEntity(): ArticlesEntity{
    return ArticlesEntity(dataSourceIdentifier, description, type, premium, links, categories, headline, images, published, lastModified, byline)
}