package com.rodrigotguerra.newsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.network.NewsListResponseSchema
import com.rodrigotguerra.newsapp.repo.NewsRepository
import com.rodrigotguerra.newsapp.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository,
    private val prefHelper: SharedPreferencesHelper
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    val newsList = MutableLiveData<List<NewsData>>()
    val newsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getBrNewsFromDatabase()
            Log.i("Data message", "Data retrieved from database")
        } else {
            getBrNewsFromRemote()
            Log.i("Data message", "Data retrieved from api")
        }
    }

    private fun getBrNewsFromDatabase() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNewsFromDB()
            fetchDataToUI(news)
        }
    }

    private fun getBrNewsFromRemote() {
        loading.value = true
        disposable.add(
            repository.getBrNewsFromApi().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsListResponseSchema>() {
                    override fun onSuccess(t: NewsListResponseSchema) {
                        storeNewsLocally(t.newsList)
                    }

                    override fun onError(e: Throwable) {
                        newsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    fun refreshByApiCall() {
        getBrNewsFromRemote()
    }

    private fun fetchDataToUI(newsListData: List<NewsData>) {
        newsList.postValue(newsListData)
        newsLoadError.postValue(false)
        loading.postValue(false)
    }

    private fun storeNewsLocally(newsListData: List<NewsData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllRecentNews()
            val result = repository.insertNews(newsListData)
            var i = 0
            while (i < newsListData.size) {
                newsListData[i].id = result[i].toInt()
                ++i
            }
            fetchDataToUI(newsListData)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}