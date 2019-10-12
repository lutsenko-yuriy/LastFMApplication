package com.yurich.lastfmapplication.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.yurich.lastfmapplication.data.database.entities.*

@Dao
abstract class AlbumsDao {

    @Transaction
    @Query("SELECT * FROM albums INNER JOIN artists")
    abstract suspend fun getAlbumsShortInfo(): List<DatabaseAlbumShortInfo>

    @Transaction
    @Query("SELECT * FROM albums INNER JOIN artists WHERE albums.id == :albumsId")
    abstract suspend fun getAlbumsDetailedInfo(albumsId: String): List<DatabaseAlbumDetailedInfo>

    @Update(onConflict = REPLACE)
    abstract suspend fun putAlbum(album: DatabaseAlbum)

    @Update(onConflict = REPLACE)
    abstract suspend fun putArtist(artist: DatabaseArtist)

    @Update(onConflict = REPLACE)
    abstract suspend fun putTracks(tracks: List<DatabaseTrack>)

    @Transaction
    suspend fun putAlbumData(albumData: DatabaseAlbumDetailedInfo) {
        putAlbum(albumData.album)
        putArtist(albumData.artist)
        putTracks(albumData.tracks)
    }

    @Delete
    abstract suspend fun removeArtist(artist: DatabaseArtist)

    @Delete
    abstract suspend fun removeAlbum(album: DatabaseAlbum)

    @Query("SELECT * FROM albums WHERE artistId == :artistId")
    abstract suspend fun getAlbumsByArtists(artistId: String): List<DatabaseAlbum>

    @Transaction
    suspend fun removeAlbumData(albumData: DatabaseAlbumDetailedInfo) {
        // According to DatabaseTrack this will remove album's tracks as well
        removeAlbum(albumData.album)

        val albums = getAlbumsByArtists(albumData.artist.id)

        if (albums.isNotEmpty()) {
            removeArtist(albumData.artist)
        }
    }

}