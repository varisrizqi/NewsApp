package com.tipiz.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import com.tipiz.newsapp.data.news.NewsRepository
import org.koin.dsl.module

val bookmarkViewModel = module {
    factory { BookmarkViewModel(get()) }
}

class BookmarkViewModel(repository: NewsRepository) : ViewModel() {

    val title = "Save"
    val articles = repository.findAll()
}