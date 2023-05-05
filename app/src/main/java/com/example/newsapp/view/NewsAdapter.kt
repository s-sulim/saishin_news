package com.example.newsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article

class NewsAdapter(
private val clickListener:(Article) ->Unit
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val news = ArrayList<Article>()
    class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(article: Article, clickListener:(Article) ->Unit) {
            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
            val ivNewsImage: ImageView = itemView.findViewById(R.id.ivImage)
            tvTitle.text = article.title
            try {
                if (article.author.equals(null)|| article.author.isEmpty()){
                    tvAuthor.text = ""
                }
                else{
                    tvAuthor.text = article.author
                }
            } catch (e: Exception) {

            }

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(ivNewsImage)
            itemView.setOnClickListener{
                clickListener(article)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentArticle = news[position]

        holder.bind(currentArticle, clickListener)

    }
    fun updateData(newData:ArrayList<Article>){
        news.clear()
        news.addAll(newData)
        notifyDataSetChanged()
    }
}