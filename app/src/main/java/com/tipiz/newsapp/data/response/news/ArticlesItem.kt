package com.tipiz.newsapp.data.response.news

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tableArticle")
@Parcelize
data class ArticlesItem(
    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("publishedAt")
    val publishedAt: String,

    @field:SerializedName("author")
    val author: String? = "",

    @field:SerializedName("urlToImage")
    val urlToImage: String? ="" ,

    @field:SerializedName("description")
    val description: String? ="",

    @field:SerializedName("source")
    val source: Source,

    @field:SerializedName("title")
    val title: String? ="",

    @field:SerializedName("url")
    val url: String? ="",

    @field:SerializedName("content")
    val content: String? =""
) : Parcelable