package com.yurich.lastfmapplication.domain.artists

import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo

data class ArtistDetailedInfo(
    val shortInfo: ArtistShortInfo,
    val albums: List<AlbumShortInfo>
)