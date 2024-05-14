package com.example.news_app.model

import java.io.Serializable

data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String?
): Serializable

data class Source(
    val id: String?,
    val name: String
)
