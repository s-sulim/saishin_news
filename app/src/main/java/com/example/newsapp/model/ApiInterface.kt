package com.example.newsapp.model

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
@GET("NewsAPI/top-headlines/category/science/in.json")
    fun getData(): Call<News>
}