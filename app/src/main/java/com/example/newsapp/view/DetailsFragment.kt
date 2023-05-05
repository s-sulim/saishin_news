package com.example.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.newsapp.R


class DetailsFragment : Fragment() {
    private lateinit var tvArticleTitle: TextView
    private lateinit var tvArticleDescription: TextView
    private lateinit var ivArticleImage: ImageView
    private lateinit var btnBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvArticleTitle = view.findViewById(R.id.tvArticleTitle)
        tvArticleDescription = view.findViewById(R.id.tvArticleDescription)
        ivArticleImage = view.findViewById(R.id.ivArticleImage)
        btnBack = view.findViewById(R.id.btnBack)
        val args = arguments
        if (args != null) {
            val articleTitle = args.getString("title")
            val articleDescription = args.getString("description")
            val articleImageUrl = args.getString("imageUrl")

            tvArticleTitle.text = articleTitle
            tvArticleDescription.text = articleDescription
            Glide.with(view.context)
                .load(articleImageUrl)
                .into( ivArticleImage)

        }
        btnBack.setOnClickListener{
            val navController: NavController = Navigation.findNavController(requireView())

            navController.navigate(R.id.action_detailsFragment_to_mainFragment)
        }
    }
}