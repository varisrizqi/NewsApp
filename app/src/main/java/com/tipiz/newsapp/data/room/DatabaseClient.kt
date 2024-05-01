package com.tipiz.newsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tipiz.newsapp.data.response.news.ArticlesItem
import com.tipiz.newsapp.data.util.SourceConverter

@Database(
    entities = [ArticlesItem::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(SourceConverter::class)
abstract class DatabaseClient : RoomDatabase() {
    abstract val newsDAO: NewsDAO

}