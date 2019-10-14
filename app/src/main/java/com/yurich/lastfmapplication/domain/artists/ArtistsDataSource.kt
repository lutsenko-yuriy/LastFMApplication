package com.yurich.lastfmapplication.domain.artists

import com.yurich.lastfmapplication.domain.status.Either
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo

interface ArtistsDataSource {

    suspend fun getArtistsByQuery(query: String, page: Int = 1, limit: Int = 30): Either<List<ArtistShortInfo>>

    suspend fun getAlbumsByArtist(artist: ArtistShortInfo, page: Int = 1, limit: Int = 30): Either<List<AlbumShortInfo>>

}