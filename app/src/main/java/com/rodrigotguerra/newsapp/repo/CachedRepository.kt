package com.rodrigotguerra.newsapp.repo

import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.models.roomdb.NewsDao

class CachedRepository(private val dao: NewsDao) : CachedRepositoryInterface {

    override fun getNewsFromDB(): List<NewsData> {
        return dao.getNews()
    }

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

    override fun getFavoriteArticles(): List<NewsData> {
        return dao.getFavoriteArticles()
    }

}