package com.rodrigotguerra.newsapp.models.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rodrigotguerra.newsapp.models.NewsData

@Dao
interface NewsDao {

    @Insert
    suspend fun insertNews(vararg news: NewsData) : List<Long>

    //update an only article on database changing the field favorite (boolean), making safe from the call deleteAllRecentNews()
    @Query("UPDATE newstable SET favorite = 1 WHERE id = :articleId")
    suspend fun insertFavorite(articleId: Int)

    @Query("DELETE FROM newstable WHERE id = :articleId")
    suspend fun deleteFavorite(articleId: Int)

    @Query("DELETE FROM newstable WHERE favorite = 0")
    suspend fun deleteAllRecentNews()

    @Query("SELECT * FROM newstable WHERE id = :articleId")
    suspend fun getArticle(articleId: Int) : NewsData

    @Query("SELECT * FROM newstable WHERE favorite = 0")
    fun getNews(): List<NewsData>

    @Query("SELECT * FROM newstable WHERE favorite = 1")
    fun getFavoriteArticles(): List<NewsData>

}