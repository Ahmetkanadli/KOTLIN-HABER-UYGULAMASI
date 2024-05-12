package com.example.news_app.service

import com.example.news_app.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsAPI {

    @GET("everything?q=bitcoin&apiKey=dfa18be16d414df0ba4141615624b195")
    fun  getData() : Call<ArticleResponse>

}