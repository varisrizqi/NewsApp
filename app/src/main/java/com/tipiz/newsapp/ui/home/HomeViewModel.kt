package com.tipiz.newsapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.newsapp.data.news.CategoryModel
import com.tipiz.newsapp.data.news.NewsRepository
import com.tipiz.newsapp.data.response.news.NewsResponse
import kotlinx.coroutines.launch
import org.koin.dsl.module
import kotlin.math.ceil

val homeViewModel = module {
    factory { HomeViewModel(get()) }
}

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {

    val title = "News"
    val category by lazy { MutableLiveData<String>() }
    val message by lazy { MutableLiveData<String>() }
    val news by lazy { MutableLiveData<NewsResponse>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    val loadMore by lazy { MutableLiveData<Boolean>() }

    init {
        category.value = ""
        message.value = null
    }

    var query = ""
    var page = 1
    var total = 1

    fun fetch() {
        if (page > 1) loadMore.value = true else loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.fetch(category.value ?: "", query, page)
                news.value = response
                val pageResults: Double = response.totalResults / 20.0
                total = ceil(pageResults).toInt()
                page++
                loading.value = false
                loadMore.value = false
            } catch (e: Exception) {
                message.value = "Terjadi kesalahan"
            }
        }
    }


    // isi dari categori dan di terjemahin
    val categories = listOf(
        CategoryModel("", "Headlines"),
        CategoryModel("Business", "Business"),
        CategoryModel("Entertainment", "Entertainment"),
        CategoryModel("General", "General"),
        CategoryModel("Health", "Health"),
        CategoryModel("Science", "Science"),
        CategoryModel("Sports", "Sports"),
        CategoryModel("Technology", "Technology")
    )
}

