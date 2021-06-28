package com.rodrigotguerra.newsapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigotguerra.newsapp.models.NewsData
import com.rodrigotguerra.newsapp.models.NewsListResponseSchema
import com.rodrigotguerra.newsapp.repo.CachedRepositoryInterface
import com.rodrigotguerra.newsapp.repo.RemoteRepositoryInterface
import com.rodrigotguerra.newsapp.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val remoteRepository: RemoteRepositoryInterface,
    private val cachedRepository: CachedRepositoryInterface,
    private val prefHelper: SharedPreferencesHelper
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val _newsList = MutableLiveData<List<NewsData>>()
    val newsList: LiveData<List<NewsData>> get() = _newsList
    private val _newsLoadError = MutableLiveData<Boolean>()
    val newsLoadError: LiveData<Boolean> get() = _newsLoadError
    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> get() = _loading

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
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val news = cachedRepository.getNewsFromDB()
            fetchDataToUI(news)
        }
    }

    private fun getBrNewsFromRemote() {
        _loading.value = true
        disposable.add(
            remoteRepository.getBrNewsFromApi().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsListResponseSchema>() {
                    override fun onSuccess(t: NewsListResponseSchema) {
                        storeNewsLocally(t.newsList)
                    }

                    override fun onError(e: Throwable) {
                        _newsLoadError.value = true
                        _loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    fun refreshByApiCall() {
        getBrNewsFromRemote()
    }

    private fun fetchDataToUI(newsListData: List<NewsData>) {
        _newsList.postValue(newsListData)
        _newsLoadError.postValue(false)
        _loading.postValue(false)
    }

    private fun storeNewsLocally(newsListData: List<NewsData>) {
        viewModelScope.launch(Dispatchers.IO) {
            cachedRepository.deleteAllRecentNews()
            val result = cachedRepository.insertNews(newsListData)
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