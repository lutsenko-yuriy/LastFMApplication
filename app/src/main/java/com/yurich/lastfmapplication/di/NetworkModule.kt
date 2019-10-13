package com.yurich.lastfmapplication.di

import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.data.network.LastFMService
import com.yurich.lastfmapplication.data.network.LastFMServiceAdapter
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(androidContext().resources.getString(R.string.last_fm_base_url))
            .build()
            .create(LastFMService::class.java)
    }

    single {
        LastFMServiceAdapter(androidContext().resources.getString(R.string.last_fm_app_key), get())
    }

    single<ArtistsDataSource> { get<LastFMServiceAdapter>() }

}