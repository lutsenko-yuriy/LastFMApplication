package com.yurich.lastfmapplication.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.data.network.LastFMService
import com.yurich.lastfmapplication.data.network.LastFMServiceAdapter
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(androidContext().resources.getString(R.string.last_fm_base_url))
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
            )
            .build()
            .create(LastFMService::class.java)
    }

    single {
        LastFMServiceAdapter(androidContext().resources.getString(R.string.last_fm_app_key), get())
    }

    single<ArtistsDataSource> { get<LastFMServiceAdapter>() }

}