package com.rodrigotguerra.newsapp.models.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigotguerra.newsapp.models.NewsData


@Database(entities = [NewsData::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}