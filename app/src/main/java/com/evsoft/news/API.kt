package com.evsoft.news

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/api/1/news")
    suspend fun getNews(@Query("apikey") apiKey: String, @Query("q") query: String): Response<NewsResponse>
}

val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://newsdata.io/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val newsAPI: NewsAPI = retrofit.create(NewsAPI::class.java)
