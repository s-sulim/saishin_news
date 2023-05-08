package com.example.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.api.ApiInterface
import com.example.newsapp.data.repository.Repository
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.view_model.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myNewsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]

        // Observe the changes to the data in the view model
        newsViewModel.getArticleListLiveData().observe(this) { articles ->
            myNewsAdapter.updateData(articles as ArrayList<Article>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        myNewsAdapter = NewsAdapter { selectedArticle: Article ->
            listItemClicked(selectedArticle)
        }
        recyclerView.adapter = myNewsAdapter

        Repository().fetchNews { articles ->
        newsViewModel.articleListLiveData.value = articles
        myNewsAdapter.updateData(articles as ArrayList<Article>)


        }
    }
    private fun listItemClicked(selectedArticle: Article){
        val navController: NavController = Navigation.findNavController(requireView())
        val args = Bundle()
        args.putString("title", selectedArticle.title)
        args.putString("description", selectedArticle.description)
        args.putString("imageUrl", selectedArticle.urlToImage)
        navController.navigate(R.id.action_mainFragment_to_detailsFragment, args);
    }
}