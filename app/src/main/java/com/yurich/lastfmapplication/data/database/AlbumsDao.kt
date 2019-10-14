package com.yurich.lastfmapplication.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.yurich.lastfmapplication.data.database.entities.*

@Dao
abstract class AlbumsDao {

    @Query(
        """
            SELECT albums.*, artists.* 
                FROM albums INNER JOIN artists 
                        ON albums.album_artistId = artists.artist_id 
            LIMIT :limit OFFSET :offset 
        """
    )
    abstract suspend fun getAlbumsShortInfoFromInterval(offset: Int, limit: Int): List<DatabaseAlbumShortInfo>

    @Query(
        """
            SELECT albums.*, artists.* 
                FROM albums INNER JOIN artists 
                        ON albums.album_artistId = artists.artist_id 
                    WHERE albums.album_id == :albumsId
        """
    )
    abstract suspend fun getAlbumsDetailedInfo(albumsId: String): List<DatabaseAlbumDetailedInfo>

    @Insert(onConflict = IGNORE)
    abstract suspend fun putAlbum(albums: List<DatabaseAlbum>)

    @Insert(onConflict = IGNORE)
    abstract suspend fun putArtist(artists: List<DatabaseArtist>)

    @Insert(onConflict = IGNORE)
    abstract suspend fun putTracks(tracks: List<DatabaseTrack>)

    open suspend fun putAlbumDetails(album: DatabaseAlbumDetailedInfo) {
        putArtist(listOf(album.artist))
        putAlbum(listOf(album.album))
        putTracks(album.tracks)
    }

    @Delete
    abstract suspend fun removeArtist(artist: DatabaseArtist)

    @Delete
    abstract suspend fun removeAlbum(album: DatabaseAlbum)

    @Query("SELECT * FROM albums WHERE album_artistId == :artistId")
    abstract suspend fun getAlbumsByArtists(artistId: String): List<DatabaseAlbum>

    open suspend fun removeAlbumData(albumData: DatabaseAlbumDetailedInfo) {
        // According to DatabaseTrack this will remove album's tracks as well
        removeAlbum(albumData.album)

        val albums = getAlbumsByArtists(albumData.artist.id)

        if (albums.isEmpty()) {
            removeArtist(albumData.artist)
        }
    }

}