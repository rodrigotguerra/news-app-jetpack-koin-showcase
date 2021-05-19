package com.rodrigotguerra.newsapp.network

import com.google.gson.annotations.SerializedName
import com.rodrigotguerra.newsapp.models.NewsData

class NewsListResponseSchema(@SerializedName("articles") val newsList: List<NewsData>) {
}