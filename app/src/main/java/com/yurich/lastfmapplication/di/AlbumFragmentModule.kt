package com.yurich.lastfmapplication.di

import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.presentation.album.AlbumViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val albumFragmentModule = module {
    viewModel { (album: AlbumShortInfo) -> AlbumViewModel(album, get(), get()) }
}