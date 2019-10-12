package com.yurich.lastfmapplication.data.database

import com.yurich.lastfmapplication.data.database.entities.DatabaseAlbum
import com.yurich.lastfmapplication.data.database.entities.DatabaseArtist
import com.yurich.lastfmapplication.data.database.entities.DatabaseTrack
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo

object TestData {
    val testDatabaseAlbum by lazy {
        DatabaseAlbum(
            "123",
            "123",
            "Meteora",
            "aaa",
            "bbb"
        )
    }

    val testDatabaseArtist by lazy {
        DatabaseArtist(
            "123",
            "Linkin Park",
            "aaa",
            "bbb"
        )
    }

    val testDatabaseTrack by lazy {
        DatabaseTrack(
            "123",
            "123",
            "123",
            "In The End"
        )
    }

    val testArtistInfo by lazy {
        ArtistShortInfo(
            "Linkin Park",
            "123",
            ArtistShortInfo.Images(
                "aaa",
                "bbb"
            )
        )
    }

    val testAlbumShortInfo by lazy {
        AlbumShortInfo(
            "Meteora",
            "123",
            AlbumShortInfo.Images(
                "aaa",
                "bbb"
            ),
            testArtistInfo
        )
    }

    val testAlbumDetailedInfo by lazy {
        AlbumDetailedInfo(
            testAlbumShortInfo,
            listOf(
                AlbumDetailedInfo.Track(
                    "123",
                    "123",
                    "In The End"
                )
            )
        )
    }
}