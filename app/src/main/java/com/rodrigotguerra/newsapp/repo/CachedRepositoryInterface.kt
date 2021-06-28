package com.rodrigotguerra.newsapp.repo

import com.rodrigotguerra.newsapp.models.NewsData

interface CachedRepositoryInterface{

    fun getNewsFromDB() : List<NewsData>

    suspend fun insertNews(news: List<NewsData>) : List<Long>

    suspend fun insertFavorite(articleId: Int)

    suspend fun deleteFavorite(articleId: Int)

    suspend fun deleteAllRecentNews()

    fun getFavoriteArticles() : List<NewsData>

    suspend fun getArticle(articleId: Int) : NewsData

}