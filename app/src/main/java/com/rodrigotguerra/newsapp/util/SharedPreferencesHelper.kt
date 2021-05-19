package com.rodrigotguerra.newsapp.util

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rodrigotguerra.newsapp.Constants.PREF_TIME

class SharedPreferencesHelper(private val prefs: SharedPreferences) {

    fun saveUpdateTime(time: Long) {
        prefs.edit(commit = true) {
            putLong(PREF_TIME, time)
        }
    }
    fun getUpdateTime() = prefs.getLong(PREF_TIME, 0)

    fun getCacheDuration() = prefs.getString("pref_cache_duration", "")

}