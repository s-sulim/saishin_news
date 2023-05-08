package com.example.newsapp.data.api

import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
@GET("NewsAPI/top-headlines/category/science/in.json")
    fun getData(): Call<News>
}