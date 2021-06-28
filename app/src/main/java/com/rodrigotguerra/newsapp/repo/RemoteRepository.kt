package com.rodrigotguerra.newsapp.repo

import com.rodrigotguerra.newsapp.models.NewsListResponseSchema
import com.rodrigotguerra.newsapp.network.NewsApi
import io.reactivex.Single

class RemoteRepository(private val newsApi: NewsApi) : RemoteRepositoryInterface {

    override fun getBrNewsFromApi(): Single<NewsListResponseSchema> {
        return newsApi.getBrTopHeadlines()
    }

}