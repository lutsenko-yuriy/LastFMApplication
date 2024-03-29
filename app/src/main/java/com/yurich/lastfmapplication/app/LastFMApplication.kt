package com.yurich.lastfmapplication.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.yurich.lastfmapplication.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LastFMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this);

        startKoin {
            androidContext(this@LastFMApplication)

            androidLogger()

            modules(listOf(
                networkModule,
                databaseModule,
                searchFragmentModule,
                artistFragmentModule,
                albumFragmentModule,
                favoritesFragmentModule
            ))
        }
    }
}