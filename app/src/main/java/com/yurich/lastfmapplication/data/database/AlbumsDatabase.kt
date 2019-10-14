package com.yurich.lastfmapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurich.lastfmapplication.data.database.entities.DatabaseAlbum
import com.yurich.lastfmapplication.data.database.entities.DatabaseArtist
import com.yurich.lastfmapplication.data.database.entities.DatabaseTrack

@Database(
    entities = [
        DatabaseArtist::class,
        DatabaseAlbum::class,
        DatabaseTrack::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AlbumsDatabase : RoomDatabase() {

    abstract fun albumsDao(): AlbumsDao

}