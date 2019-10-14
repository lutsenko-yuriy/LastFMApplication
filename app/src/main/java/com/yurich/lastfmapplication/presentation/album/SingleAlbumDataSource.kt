package com.yurich.lastfmapplication.presentation.album

import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsDataSource
import com.yurich.lastfmapplication.domain.status.Either

class SingleAlbumDataSource(
    private val localStorage: AlbumsDataSource,
    private val networkStorage: AlbumsDataSource
) {

    suspend fun getAlbumDetailedInfo(album: AlbumShortInfo): AlbumStatus {
        val albumFromLocalStorage = localStorage.getAlbumDetailedInfo(album)

        if (albumFromLocalStorage is Either.Result) {
            return AlbumStatus(albumFromLocalStorage, Source.LOCAL_STORAGE)
        } else {
            return AlbumStatus(networkStorage.getAlbumDetailedInfo(album), Source.NETWORK)
        }
    }

    data class AlbumStatus(
        val result: Either<AlbumDetailedInfo>,
        val source: Source
    )

    enum class Source {
        NETWORK, LOCAL_STORAGE
    }
}