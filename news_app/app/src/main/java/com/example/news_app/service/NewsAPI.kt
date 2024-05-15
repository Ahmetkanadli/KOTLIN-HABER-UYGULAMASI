package com.example.news_app.service

import com.example.news_app.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("everything")
    fun getData(@Query("q") query: String, @Query("apiKey") apiKey: String): Call<ArticleResponse>

}

