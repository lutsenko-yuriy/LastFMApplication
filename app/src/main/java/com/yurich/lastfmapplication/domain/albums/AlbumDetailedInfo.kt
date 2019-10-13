package com.yurich.lastfmapplication.domain.albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumDetailedInfo(
    val shortInfo: AlbumShortInfo,
    val tracks: List<Track>
) : Parcelable {

    @Parcelize
    data class Track(
        val id: String,
        val duration: String,
        val name: String
    ) : Parcelable
}