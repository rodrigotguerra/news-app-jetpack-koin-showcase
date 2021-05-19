package com.rodrigotguerra.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.repo.NewsRepository
import kotlinx.coroutines.launch

class ArticleDetailsViewModel(private val repository: NewsRepository) : ViewModel() {

    val articleLiveData = MutableLiveData<NewsData>()
    val isFavorite = MutableLiveData<Boolean>()

    fun fetch(articleId: Int) {
        viewModelScope.launch {
            val article = repository.getArticle(articleId)
            articleLiveData.postValue(article)
            isFavorite.postValue(article.favorite)
        }
    }

    fun favoriteAnArticle() {
        viewModelScope.launch {
            articleLiveData.value?.let { article ->
                val articleId = article.id
                isFavorite.value?.let {
                    if (!it) {
                        repository.insertFavorite(articleId)
                        isFavorite.postValue(!it)
                    }
                    else {
                        repository.deleteFavorite(articleId)
                        isFavorite.postValue(!it)
                    }
                }
            }
        }
    }

}