package com.example.newsapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.Article

class NewsViewModel: ViewModel() {

    val articleListLiveData: MutableLiveData<List<Article>> = MutableLiveData()
    fun getArticleListLiveData(): LiveData<List<Article>> {
        return articleListLiveData
    }
}