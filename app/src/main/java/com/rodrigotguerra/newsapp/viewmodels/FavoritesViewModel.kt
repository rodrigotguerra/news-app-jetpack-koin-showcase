package com.rodrigotguerra.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.repo.CachedRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: CachedRepositoryInterface) : ViewModel() {

    private val _favoritesList = MutableLiveData<List<NewsData>>()
    val favoritesList : LiveData<List<NewsData>> get() = _favoritesList
    private val _noFavoritesFound = MutableLiveData<Boolean>()
    val noFavoritesFound: LiveData<Boolean> get() = _noFavoritesFound
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getFavoritesFromDB() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val favoritesData = repository.getFavoriteArticles()
            if (favoritesData.isEmpty()) {
                _loading.postValue(false)
                _noFavoritesFound.postValue(true)
            } else {
                _loading.postValue(false)
                _noFavoritesFound.postValue(false)
                _favoritesList.postValue(favoritesData)
            }
        }
    }
}