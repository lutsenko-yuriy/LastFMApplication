package com.yurich.lastfmapplication.domain.albums

import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo

data class AlbumShortInfo(
    val name: String,
    val id: String,
    val images: Images,
    val artistShortInfo: ArtistShortInfo
) {

    data class Images(
        val previewUrl: String,
        val coverUrl: String
    )

}