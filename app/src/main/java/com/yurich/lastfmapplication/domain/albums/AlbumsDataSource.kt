package com.yurich.lastfmapplication.domain.albums

import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo

interface AlbumsDataSource {

    suspend fun getTracksByAlbum(artist: ArtistShortInfo, album: AlbumShortInfo): AlbumDetailedInfo

}