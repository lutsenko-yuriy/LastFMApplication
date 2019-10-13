package com.yurich.lastfmapplication.domain.artists

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArtistShortInfo(
    val name: String,
    val id: String,
    val images: Images
) : Parcelable {

    @Parcelize
    data class Images(
        val previewUrl: String,
        val coverUrl: String
    ) : Parcelable

}