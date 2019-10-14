package com.yurich.lastfmapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = DatabaseArtist::class,
            parentColumns = ["artist_id"],
            childColumns = ["album_artistId"],
            onDelete = CASCADE
        )
    ]
)
data class DatabaseAlbum(
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    val id: String,
    @ColumnInfo(name = "album_artistId")
    val artistId: String,
    @ColumnInfo(name = "album_name")
    val name: String,
    @ColumnInfo(name = "album_previewUrl")
    val previewUrl: String,
    @ColumnInfo(name = "album_coverUrl")
    val coverUrl: String
)