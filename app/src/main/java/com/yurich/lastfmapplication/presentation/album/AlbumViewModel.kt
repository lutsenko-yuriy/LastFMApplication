package com.yurich.lastfmapplication.presentation.album

import androidx.lifecycle.ViewModel
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsDataSource

class AlbumViewModel(
    album: AlbumShortInfo,
    private val albumsDataSource: AlbumsDataSource
) : ViewModel() {
    // TODO: Implement the ViewModel
}
