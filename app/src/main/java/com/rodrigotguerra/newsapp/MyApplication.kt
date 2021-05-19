package com.rodrigotguerra.newsapp

import android.app.Application
import com.rodrigotguerra.newsapp.dependencyinjection.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)

            modules(mainModule)
        }
    }
}