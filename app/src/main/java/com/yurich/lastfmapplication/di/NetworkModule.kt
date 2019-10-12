package com.yurich.lastfmapplication.di

import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.data.network.LastFMServiceAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(androidContext().resources.getString(R.string.last_fm_base_url))
            .build()
    }

    single {
        LastFMServiceAdapter(androidContext().resources.getString(R.string.last_fm_app_key), get())
    }
}