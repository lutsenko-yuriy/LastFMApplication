package com.yurich.lastfmapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    foreignKeys = [
        ForeignKey(
            entity = DatabaseAlbum::class,
            parentColumns = ["album_id"],
            childColumns = ["track_albumId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DatabaseTrack(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val id: String,
    @ColumnInfo(name = "track_albumId")
    val albumId: String,
    @ColumnInfo(name = "track_duration")
    val duration: String,
    @ColumnInfo(name = "track_name")
    val name: String
)