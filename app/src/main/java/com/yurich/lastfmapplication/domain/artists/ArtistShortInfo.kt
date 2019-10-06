package com.yurich.lastfmapplication.domain.artists

data class ArtistShortInfo(
    val name: String,
    val id: String,
    val images: Images
) {

    data class Images(
        val previewUrl: String,
        val coverUrl: String
    )

}