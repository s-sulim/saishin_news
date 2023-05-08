package com.example.newsapp.data.repository
import com.example.newsapp.data.api.RetrofitInstance
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val api = RetrofitInstance.api

    fun fetchNews(callback: (List<Article>) -> Unit) {
        api.getData().enqueue(object : Callback<News?> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                val responseBody = response.body()!!
                callback(responseBody.articles)
            }
            override fun onFailure(call: Call<News?>, t: Throwable) {
            }
        })
    }
}