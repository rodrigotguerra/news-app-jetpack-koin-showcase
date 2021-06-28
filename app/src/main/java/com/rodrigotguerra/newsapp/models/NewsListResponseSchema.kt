package com.rodrigotguerra.newsapp.models

import com.google.gson.annotations.SerializedName
import com.rodrigotguerra.newsapp.models.NewsData

class NewsListResponseSchema(@SerializedName("articles") val newsList: List<NewsData>) {
}