package com.tipiz.newsapp.data.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tipiz.newsapp.data.response.news.Source

// convert Source ke Entity
object SourceConverter {

    @TypeConverter
    @JvmStatic
    fun toSource(value: String?): Source {
        val modelType = object : TypeToken<Source>() {}.type
        return Gson().fromJson(value, modelType)
    }

    @TypeConverter
    @JvmStatic
    fun fromSource(source: Source): String {
        val gson = Gson()
        return gson.toJson(source)
    }

}