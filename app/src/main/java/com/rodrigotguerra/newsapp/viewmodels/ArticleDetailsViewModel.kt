package com.rodrigotguerra.newsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.repo.CachedRepositoryInterface
import kotlinx.coroutines.launch

class ArticleDetailsViewModel(private val repository: CachedRepositoryInterface) : ViewModel() {

    private val _articleLiveData = MutableLiveData<NewsData>()
    val articleLiveData: LiveData<NewsData> get() = _articleLiveData
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun fetch(articleId: Int) {
        viewModelScope.launch {
            val article = repository.getArticle(articleId)
            _articleLiveData.postValue(article)
            _isFavorite.postValue(article.favorite)
        }
    }

    fun favoriteAnArticle() {
        viewModelScope.launch {
            _articleLiveData.value?.let { article ->
                val articleId = article.id
                _isFavorite.value?.let {
                    if (!it) repository.insertFavorite(articleId) else repository.deleteFavorite(articleId)
                    _isFavorite.postValue(!it)
                }
            }
        }
    }

}