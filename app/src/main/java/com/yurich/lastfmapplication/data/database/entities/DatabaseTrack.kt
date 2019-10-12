package com.yurich.lastfmapplication.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    foreignKeys = [
        ForeignKey(
            entity = DatabaseAlbum::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DatabaseTrack(
    @PrimaryKey
    val id: String,
    val albumId: String,
    val duration: String,
    val name: String
)