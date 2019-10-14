package com.yurich.lastfmapplication.di

import com.yurich.lastfmapplication.presentation.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesFragmentModule = module {
    viewModel { FavoritesViewModel(get()) }
}