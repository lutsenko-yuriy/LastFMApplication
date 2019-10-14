package com.yurich.lastfmapplication.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DatabaseAlbumDetailedInfo(
    @Embedded
    val album: DatabaseAlbum,
    @Embedded
    val artist: DatabaseArtist,
    @Relation(entity = DatabaseTrack::class, parentColumn = "album_id", entityColumn = "track_albumId")
    val tracks: List<DatabaseTrack>
)