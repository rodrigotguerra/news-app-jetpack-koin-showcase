package com.rodrigotguerra.newsapp.repo

import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.network.NewsListResponseSchema
import io.reactivex.Single

interface NewsRepositoryInterface {

    suspend fun insertNews(news: List<NewsData>) : List<Long>

    suspend fun insertFavorite(articleId: Int)

    suspend fun deleteFavorite(articleId: Int)

    suspend fun deleteAllRecentNews()

    suspend fun getArticle(articleId: Int) : NewsData

    fun getNewsFromDB() : List<NewsData>

    fun getFavoriteArticles() : List<NewsData>

    fun getBrNewsFromApi() : Single<NewsListResponseSchema>

}