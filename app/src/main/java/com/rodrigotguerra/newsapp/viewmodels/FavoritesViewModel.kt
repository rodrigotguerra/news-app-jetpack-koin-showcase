package com.rodrigotguerra.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.repo.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: NewsRepository) : ViewModel() {

    val favoritesList = MutableLiveData<List<NewsData>>()
    val noFavoritesFound = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getFavoritesFromDB() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val favoritesData = repository.getFavoriteArticles()
            if (favoritesData.isEmpty()) {
                loading.postValue(false)
                noFavoritesFound.postValue(true)
            } else {
                loading.postValue(false)
                noFavoritesFound.postValue(false)
                favoritesList.postValue(favoritesData)
            }
        }
    }
}