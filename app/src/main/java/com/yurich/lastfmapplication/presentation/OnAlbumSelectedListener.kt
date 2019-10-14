package com.yurich.lastfmapplication.presentation

import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo

interface OnAlbumSelectedListener {
    fun onAlbumSelected(album: AlbumShortInfo)
}