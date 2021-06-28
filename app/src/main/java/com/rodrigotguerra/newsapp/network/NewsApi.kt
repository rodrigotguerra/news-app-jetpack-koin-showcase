package com.rodrigotguerra.newsapp.network

import com.rodrigotguerra.newsapp.Constants.NEWS_API_KEY
import com.rodrigotguerra.newsapp.models.NewsListResponseSchema
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {
    @GET("top-headlines?country=br&apiKey=$NEWS_API_KEY")
    fun getBrTopHeadlines() : Single<NewsListResponseSchema>
}