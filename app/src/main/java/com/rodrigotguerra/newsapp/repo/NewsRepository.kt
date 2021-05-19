package com.rodrigotguerra.newsapp.repo

import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.models.roomdb.NewsDao
import com.rodrigotguerra.newsapp.network.NewsApi
import com.rodrigotguerra.newsapp.network.NewsListResponseSchema
import io.reactivex.Single

class NewsRepository(private val newsApi: NewsApi, private val dao: NewsDao) : NewsRepositoryInterface {

    override suspend fun insertNews(news: List<NewsData>) : List<Long>{
        return dao.insertNews(*news.toTypedArray())
    }

    override suspend fun insertFavorite(articleId: Int) {
        dao.insertFavorite(articleId)
    }

    override suspend fun deleteFavorite(articleId: Int) {
        dao.deleteFavorite(articleId)
    }

    override suspend fun deleteAllRecentNews() {
        dao.deleteAllRecentNews()
    }

    override suspend fun getArticle(articleId: Int): NewsData {
        return dao.getArticle(articleId)
    }

    override fun getNewsFromDB(): List<NewsData> {
        return dao.getNews()
    }

    override fun getFavoriteArticles(): List<NewsData> {
        return dao.getFavoriteArticles()
    }

    override fun getBrNewsFromApi(): Single<NewsListResponseSchema> {
        return newsApi.getBrTopHeadlines()
    }

}