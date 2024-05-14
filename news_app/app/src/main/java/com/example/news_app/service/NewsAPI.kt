package com.example.news_app.service

import com.example.news_app.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    //everything?q=bitcoin&apiKey=dfa18be16d414df0ba4141615624b195
    @GET("everything")
    fun getData(@Query("q") query: String, @Query("apiKey") apiKey: String): Call<ArticleResponse>

}

