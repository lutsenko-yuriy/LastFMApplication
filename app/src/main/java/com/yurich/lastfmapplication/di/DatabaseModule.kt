package com.yurich.lastfmapplication.di

import androidx.room.Room
import com.yurich.lastfmapplication.data.database.AlbumsDaoProxy
import com.yurich.lastfmapplication.data.database.AlbumsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AlbumsDatabase::class.java,
            "albums.db"
        ).build()
    }

    single {
        get<AlbumsDatabase>().albumsDao()
    }

    single {
        AlbumsDaoProxy(get())
    }
}