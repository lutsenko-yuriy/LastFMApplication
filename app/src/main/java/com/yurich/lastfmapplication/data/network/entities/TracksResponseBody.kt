package com.yurich.lastfmapplication.data.network.entities
import com.google.gson.annotations.SerializedName


data class TracksResponseBody(
    @SerializedName("album")
    val album: Album
) {

    data class Album(
        @SerializedName("artist")
        val artist: String,
        @SerializedName("image")
        val image: List<Image>,
        @SerializedName("mbid")
        val mbid: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("tracks")
        val tracks: Tracks,
        @SerializedName("url")
        val url: String
    )

    data class Image(
        @SerializedName("size")
        val size: String,
        @SerializedName("#text")
        val text: String
    )

    data class Tracks(
        @SerializedName("track")
        val track: List<Track>
    )

    data class Track(
        @SerializedName("url")
        val id: String,
        @SerializedName("artist")
        val artist: Artist,
        @SerializedName("duration")
        val duration: String,
        @SerializedName("name")
        val name: String
    )

    data class Artist(
        @SerializedName("url")
        val id: String,
        @SerializedName("name")
        val name: String
    )

}