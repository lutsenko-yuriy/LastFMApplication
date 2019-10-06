package com.yurich.lastfmapplication.domain.artists

import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo

interface ArtistsDataSource {

    suspend fun getArtistsByQuery(query: String, page: Int, limit: Int): List<ArtistShortInfo>

    suspend fun getAlbumsByArtist(artistShortInfo: ArtistShortInfo, page: Int, limit: Int): List<AlbumShortInfo>

}