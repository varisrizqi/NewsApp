package com.tipiz.newsapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tipiz.newsapp.data.response.news.ArticlesItem

@Dao
interface NewsDAO {

    @Query("SELECT * FROM tableArticle")
    fun findAll(): LiveData<List<ArticlesItem>>

    //untuk menemukan/menampilkan data yang telah di simpan
    @Query("select count(*) from tableArticle where publishedAt =:publish ")
    suspend fun find(publish: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(news: ArticlesItem)

    @Delete
    suspend fun remove(news: ArticlesItem)

}