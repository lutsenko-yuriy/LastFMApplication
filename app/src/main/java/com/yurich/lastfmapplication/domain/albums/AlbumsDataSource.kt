package com.yurich.lastfmapplication.domain.albums

import com.yurich.lastfmapplication.data.network.LastFMServiceAdapter

interface AlbumsDataSource {

    suspend fun getTracksByAlbum(album: AlbumShortInfo): LastFMServiceAdapter.Either<AlbumDetailedInfo>

}