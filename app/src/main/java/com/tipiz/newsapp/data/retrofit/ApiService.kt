package com.tipiz.newsapp.data.retrofit

import com.tipiz.newsapp.data.response.news.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") q: String,
        @Query("page") page: Int
    ): NewsResponse
}