package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.models.Category
import com.example.models.Image
import com.example.models.Links

/**
 * ArticlesEntity
 * Database Entity for the Articles Table
 */
@Entity(tableName = "articles_table")
data class ArticlesEntity(
    @PrimaryKey
    @ColumnInfo(name = "datasourceidentifier")
    val dataSourceIdentifier: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "premium")
    val premium: Boolean,
    @ColumnInfo(name = "links")
    val links: Links?,
    @ColumnInfo(name = "categories")
    val categories: List<Category>,
    @ColumnInfo(name = "headline")
    val headline: String,
    @ColumnInfo(name = "images")
    val images: List<Image>,
    @ColumnInfo(name = "published")
    val published: String,
    @ColumnInfo(name = "lastModified")
    val lastModified: String,
    @ColumnInfo(name = "byline")
    val byline: String?
)