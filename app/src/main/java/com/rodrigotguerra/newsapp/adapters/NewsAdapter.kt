package com.rodrigotguerra.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotguerra.newsapp.R
import com.rodrigotguerra.newsapp.databinding.NewsListItemBinding
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.views.NewsFragmentDirections

class NewsAdapter(private val newsList: ArrayList<NewsData>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(), NewsClickListener {


    class NewsViewHolder(var view: NewsListItemBinding) : RecyclerView.ViewHolder(view.root)

    fun bindData(newsDataList: List<NewsData>) {
        newsList.clear()
        newsList.addAll(newsDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<NewsListItemBinding>(inflater, R.layout.news_list_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.view.article = newsList[position]

        holder.itemView.setOnClickListener {
            onNewsClicked(holder.itemView, newsList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onNewsClicked(view: View, articleId: Int) {
        val action = NewsFragmentDirections.actionNewsFragmentToArticleDetailsFragment()
        action.articleId = articleId
        Navigation.findNavController(view).navigate(action)
    }
}