package com.yurich.lastfmapplication.data.network.entities

import com.google.gson.annotations.SerializedName


data class ArtistsResponseBody(
    @SerializedName("results")
    val results: Results
) {

    data class Results(
        @SerializedName("artistmatches")
        val artistMatches: ArtistMatches
    )

    data class ArtistMatches(
        @SerializedName("artist")
        val artist: List<NetworkArtist>
    )

    data class NetworkArtist(
        @SerializedName("image")
        val image: List<Image>,
        @SerializedName("url")
        val id: String,
        @SerializedName("name")
        val name: String
    ) {

        data class Image(
            @SerializedName("size")
            val size: String,
            @SerializedName("#text")
            val text: String
        )

    }

}
