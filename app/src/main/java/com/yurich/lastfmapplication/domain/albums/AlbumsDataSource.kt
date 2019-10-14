package com.yurich.lastfmapplication.domain.albums

import com.yurich.lastfmapplication.domain.status.Either

interface AlbumsDataSource {

    suspend fun getAlbumDetailedInfo(album: AlbumShortInfo): Either<AlbumDetailedInfo>

}