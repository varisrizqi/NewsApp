package com.tipiz.newsapp.data.room

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideNews( get()) }
}

fun provideDatabase(application: Application): DatabaseClient {
    return Room.databaseBuilder(
        application,
        DatabaseClient::class.java,
        "newsKoin.db"
    ).fallbackToDestructiveMigration() //migrasi atau ditimpa datanya, yg ini di replace
        .build()
}

fun provideNews(databaseClient: DatabaseClient): NewsDAO {
    return databaseClient.newsDAO
}