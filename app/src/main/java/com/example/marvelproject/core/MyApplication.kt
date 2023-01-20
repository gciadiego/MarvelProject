package com.example.marvelproject.core

import android.app.Application
import com.example.marvelproject.core.di.apiModule
import com.example.marvelproject.core.di.networkModule
import com.example.marvelproject.core.di.repositoryModule
import com.example.marvelproject.core.di.vmModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@HiltAndroidApp
class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        //Initialize Koin in the application
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(apiModule,
                networkModule,
                repositoryModule,
                vmModule)
        }
    }
}