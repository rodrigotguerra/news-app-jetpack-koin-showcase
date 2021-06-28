package com.rodrigotguerra.newsapp.repo

import com.rodrigotguerra.newsapp.models.NewsListResponseSchema
import io.reactivex.Single

interface RemoteRepositoryInterface {

    fun getBrNewsFromApi() : Single<NewsListResponseSchema>

}