package com.yurich.lastfmapplication.di

import com.yurich.lastfmapplication.presentation.search_container.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchFragmentModule = module {
    viewModel { SearchViewModel() }
}