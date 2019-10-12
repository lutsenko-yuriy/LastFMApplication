package com.yurich.lastfmapplication.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "artists"
)
data class DatabaseArtist(
    @PrimaryKey
    val id: String,
    val name: String
)