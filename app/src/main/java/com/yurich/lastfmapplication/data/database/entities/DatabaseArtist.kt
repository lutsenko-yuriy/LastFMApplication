package com.yurich.lastfmapplication.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "artists"
)
data class DatabaseArtist(
    @PrimaryKey
    @ColumnInfo(name = "artist_id")
    val id: String,
    @ColumnInfo(name = "artist_name")
    val name: String,
    @ColumnInfo(name = "artist_previewUrl")
    val previewUrl: String,
    @ColumnInfo(name = "artist_coverUrl")
    val coverUrl: String
)