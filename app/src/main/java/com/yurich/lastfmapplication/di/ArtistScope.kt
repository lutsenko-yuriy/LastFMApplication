package com.yurich.lastfmapplication.di

import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.presentation.artist.ArtistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val artistFragmentModule = module {
    viewModel { (artistInfo: ArtistShortInfo) -> ArtistViewModel(artistInfo, get()) }
}