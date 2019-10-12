package com.yurich.lastfmapplication.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = DatabaseArtist::class,
            parentColumns = ["id"],
            childColumns = ["artistId"],
            onDelete = CASCADE
        )
    ]
)
data class DatabaseAlbum(
    @PrimaryKey
    val id: String,
    val artistId: String,
    val name: String,
    val previewUrl: String,
    val coverUrl: String
)