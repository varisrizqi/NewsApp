package com.tipiz.newsapp.data.news

import com.tipiz.newsapp.BuildConfig
import com.tipiz.newsapp.data.response.news.ArticlesItem
import com.tipiz.newsapp.data.response.news.NewsResponse
import com.tipiz.newsapp.data.retrofit.ApiService
import com.tipiz.newsapp.data.room.NewsDAO
import org.koin.dsl.module

val newsRepositoryModule = module {
    factory { NewsRepository(get(), get()) }
}

class NewsRepository(
    private val apiService: ApiService,
    private val db: NewsDAO
) {


    suspend fun fetch(
        category: String,
        query: String,
        page: Int
    ): NewsResponse {
        return apiService.fetchNews(
            BuildConfig.API_KEY,
            "us",
            category,
            query,
            page
        )

    }

    fun findAll() = db.findAll()

    suspend fun find(news: ArticlesItem) = db.find(news.publishedAt)

    suspend fun save(news: ArticlesItem) {
        db.save(news)
    }

    suspend fun remove(news: ArticlesItem) {
        db.remove(news)
    }

}