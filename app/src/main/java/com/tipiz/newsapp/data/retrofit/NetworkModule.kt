package com.tipiz.newsapp.data.retrofit

import com.tipiz.newsapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//koin 1/2
// koin, cara melakukan injection di beberapa instance/object
val networkModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit( get() ) }
    single { provideNewsApi(get()) }
}


//instance loggingInterceptor
fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}

//instance retrofit
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

//instance interface
fun provideNewsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)