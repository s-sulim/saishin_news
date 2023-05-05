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
import com.example.newsapp.model.ApiInterface
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.view_model.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://saurav.tech/"
class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myNewsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel
    val articles = ArrayList<Article>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        myNewsAdapter = NewsAdapter { selectedArticle: Article ->                        //assigning our adapter as an adapter for rv
            listItemClicked(selectedArticle)
        }
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = myNewsAdapter
        fetchNews()
    }
    private fun listItemClicked(selectedArticle: Article){
        val navController: NavController = Navigation.findNavController(requireView())
        val args = Bundle()
        args.putString("title", selectedArticle.title)
        args.putString("description", selectedArticle.description)
        args.putString("imageUrl", selectedArticle.urlToImage)
        navController.navigate(R.id.action_mainFragment_to_detailsFragment, args);

    }
    private fun fetchNews() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<News?> {
            override fun onResponse(
                call: Call<News?>,
                response: Response<News?>
            ) {
                val responseBody = response.body()!!
                for (myData in responseBody.articles){
                    articles.add(myData)
                    newsViewModel.getArticleListLiveData().observe(viewLifecycleOwner, { articles ->
                        myNewsAdapter.updateData(articles)
                    })

                }
                newsViewModel.articleListLiveData.value = articles
            }
            override fun onFailure(call: Call<News?>, t: Throwable) {
                Toast.makeText(requireContext(),"Couldn't connect to the internet.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}