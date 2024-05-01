package com.tipiz.newsapp.data.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey



class NewsEntity(
    @field:ColumnInfo("title")
    val title: String,

    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo("publishedAt")
    val publishedAt: String,

    @field:ColumnInfo("urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo("url")
    val url: String? = null,

)
