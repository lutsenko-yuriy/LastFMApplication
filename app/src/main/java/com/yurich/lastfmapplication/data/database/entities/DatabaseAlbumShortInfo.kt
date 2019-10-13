package com.yurich.lastfmapplication.data.database.entities

import androidx.room.Embedded

data class DatabaseAlbumShortInfo(
    @Embedded val album: DatabaseAlbum,
    @Embedded val artist: DatabaseArtist
)