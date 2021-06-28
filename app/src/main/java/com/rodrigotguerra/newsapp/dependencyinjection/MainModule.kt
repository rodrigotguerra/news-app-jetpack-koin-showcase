package com.rodrigotguerra.newsapp.dependencyinjection

import androidx.preference.PreferenceManager
import androidx.room.Room
import com.rodrigotguerra.newsapp.Constants.NEWS_API_BASE_URL
import com.rodrigotguerra.newsapp.Constants.NEWS_DATABASE
import com.rodrigotguerra.newsapp.models.roomdb.NewsDatabase
import com.rodrigotguerra.newsapp.network.NewsApi
import com.rodrigotguerra.newsapp.repo.CachedRepository
import com.rodrigotguerra.newsapp.repo.RemoteRepository
import com.rodrigotguerra.newsapp.util.SharedPreferencesHelper
import com.rodrigotguerra.newsapp.viewmodels.ArticleDetailsViewModel
import com.rodrigotguerra.newsapp.viewmodels.FavoritesViewModel
import com.rodrigotguerra.newsapp.viewmodels.NewsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single {
        Retrofit.Builder().baseUrl(NEWS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(NewsApi::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), NewsDatabase::class.java, NEWS_DATABASE).build()
    }

    single {
        get<NewsDatabase>().newsDao()
    }

    single {
        SharedPreferencesHelper(PreferenceManager.getDefaultSharedPreferences(androidContext()))
    }

    factory {
        RemoteRepository(get())
    }

    factory {
        CachedRepository(get())
    }

    viewModel {
        NewsViewModel(
            remoteRepository = get<RemoteRepository>(),
            cachedRepository = get<CachedRepository>(),
            prefHelper = get()
        )
    }

    viewModel {
        ArticleDetailsViewModel(
            repository = get<CachedRepository>()
        )
    }

    viewModel {
        FavoritesViewModel(
            repository = get<CachedRepository>()
        )
    }
}