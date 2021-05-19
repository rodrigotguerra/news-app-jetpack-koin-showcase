package com.rodrigotguerra.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rodrigotguerra.newsapp.Constants.NEWS_TABLE

@Entity(tableName = NEWS_TABLE)
data class NewsData(
    val author: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val content: String?,
    val favorite: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)