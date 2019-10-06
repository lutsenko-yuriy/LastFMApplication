package com.yurich.lastfmapplication.data.entities

import com.google.gson.annotations.SerializedName


data class TopAlbumsResponseBody(
    @SerializedName("topalbums")
    val topAlbums: TopAlbums
) {

    data class TopAlbums(
        @SerializedName("album")
        val album: List<Album>
    )

    data class Album(
        @SerializedName("artist")
        val artist: Artist,
        @SerializedName("image")
        val image: List<Image>,
        @SerializedName("mbid")
        val mbid: String,
        @SerializedName("name")
        val name: String
    )

    data class Artist(
        @SerializedName("mbid")
        val mbid: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String
    )

    data class Image(
        @SerializedName("size")
        val size: String,
        @SerializedName("#text")
        val text: String
    )

}