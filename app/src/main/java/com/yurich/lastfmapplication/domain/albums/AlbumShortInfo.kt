package com.yurich.lastfmapplication.domain.albums

import android.os.Parcelable
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumShortInfo(
    val name: String,
    val id: String,
    val images: Images,
    val artistShortInfo: ArtistShortInfo
) : Parcelable {

    @Parcelize
    data class Images(
        val previewUrl: String,
        val coverUrl: String
    ) : Parcelable

}