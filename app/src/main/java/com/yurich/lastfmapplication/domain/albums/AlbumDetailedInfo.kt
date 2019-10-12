package com.yurich.lastfmapplication.domain.albums

data class AlbumDetailedInfo(
    val shortInfo: AlbumShortInfo,
    val tracks: List<Track>
) {

    data class Track(
        val duration: String,
        val name: String
    )
}