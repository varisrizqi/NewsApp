package com.tipiz.newsapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.tipiz.newsapp.data.news.newsRepositoryModule
import com.tipiz.newsapp.data.retrofit.networkModule
import com.tipiz.newsapp.data.room.databaseModule
import com.tipiz.newsapp.ui.bookmark.bookmarkModule
import com.tipiz.newsapp.ui.bookmark.bookmarkViewModel
import com.tipiz.newsapp.ui.detail.detailModule
import com.tipiz.newsapp.ui.detail.detailViewModel
import com.tipiz.newsapp.ui.home.homeModule
import com.tipiz.newsapp.ui.home.homeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        startKoin {
            androidLogger() // untuk mengecek jika terjadi error
            androidContext(this@NewsApp)
            modules(
                networkModule,
                newsRepositoryModule,
                homeViewModel,
                homeModule,
                bookmarkViewModel,
                bookmarkModule,
                databaseModule,
                detailViewModel,
                detailModule
            )
        }
    }

}