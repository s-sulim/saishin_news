package com.example.newsapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.Article

class NewsViewModel: ViewModel() {

    val articleListLiveData: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    fun getArticleListLiveData(): LiveData<ArrayList<Article>> {
        return articleListLiveData
    }
}