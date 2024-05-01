package com.tipiz.newsapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tipiz.newsapp.data.news.NewsRepository
import com.tipiz.newsapp.data.response.news.ArticlesItem
import kotlinx.coroutines.launch
import org.koin.dsl.module

val detailViewModel = module {
    factory { DetailViewModel(get()) }
}

class DetailViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val isBookmark by lazy { MutableLiveData(0) }


    fun find(news: ArticlesItem) {
        viewModelScope.launch {
            isBookmark.value = repository.find(news)
            repository.find(news)
        }
    }

    fun bookmark(news: ArticlesItem) {

        viewModelScope.launch {
            if (isBookmark.value == 0) repository.save(news)
            else repository.remove(news)
            find(news)
        }

    }



}