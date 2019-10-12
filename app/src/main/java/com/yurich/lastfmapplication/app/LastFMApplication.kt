package com.yurich.lastfmapplication.app

import android.app.Application
import com.yurich.lastfmapplication.di.networkModule
import com.yurich.lastfmapplication.di.searchFragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LastFMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LastFMApplication)

            androidLogger()

            modules(listOf(
                searchFragmentModule,
                networkModule
            ))
        }
    }
}