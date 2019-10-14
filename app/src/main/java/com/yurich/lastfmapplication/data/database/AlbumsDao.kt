package com.yurich.lastfmapplication.data.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.yurich.lastfmapplication.data.database.entities.*

@Dao
abstract class AlbumsDao {

    @Transaction
    @Query("SELECT albums.*, artists.* FROM albums INNER JOIN artists ON albums.album_artistId = artists.artist_id")
    abstract suspend fun getAlbumsShortInfo(): List<DatabaseAlbumShortInfo>

    @Transaction
    @Query("SELECT albums.*, artists.* FROM albums INNER JOIN artists ON albums.album_artistId = artists.artist_id WHERE albums.album_id == :albumsId")
    abstract suspend fun getAlbumsDetailedInfo(albumsId: String): List<DatabaseAlbumDetailedInfo>

    @Update(onConflict = REPLACE)
    abstract suspend fun putAlbum(album: DatabaseAlbum)

    @Update(onConflict = REPLACE)
    abstract suspend fun putArtist(artist: DatabaseArtist)

    @Update(onConflict = REPLACE)
    abstract suspend fun putTracks(tracks: List<DatabaseTrack>)

    @Transaction
    open suspend fun putAlbumDetails(album: DatabaseAlbumDetailedInfo) {
        putAlbum(album.album)
        putArtist(album.artist)
        putTracks(album.tracks)
    }

            @Delete
    abstract suspend fun removeArtist(artist: DatabaseArtist)

    @Delete
    abstract suspend fun removeAlbum(album: DatabaseAlbum)

    @Query("SELECT * FROM albums WHERE album_artistId == :artistId")
    abstract suspend fun getAlbumsByArtists(artistId: String): List<DatabaseAlbum>

    @Transaction
    open suspend fun removeAlbumData(albumData: DatabaseAlbumDetailedInfo) {
        // According to DatabaseTrack this will remove album's tracks as well
        removeAlbum(albumData.album)

        val albums = getAlbumsByArtists(albumData.artist.id)

        if (albums.isNotEmpty()) {
            removeArtist(albumData.artist)
        }
    }

}