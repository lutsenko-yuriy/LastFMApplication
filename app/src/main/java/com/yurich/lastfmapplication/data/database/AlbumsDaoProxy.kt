package com.yurich.lastfmapplication.data.database

import com.yurich.lastfmapplication.domain.status.Either
import com.yurich.lastfmapplication.data.database.entities.*
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsCrudInterface
import com.yurich.lastfmapplication.domain.albums.AlbumsDataSource
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AlbumsDaoProxy(
    private val dao: AlbumsDao
) : AlbumsCrudInterface, AlbumsDataSource {

    override suspend fun getAllAlbums() = withContext(IO) {
        dao.getAlbumsShortInfo().map { it.toAlbumShortInfo() }
    }

    override suspend fun putAlbum(album: AlbumDetailedInfo) = withContext(IO) {
        dao.putAlbumData(album.toDatabaseAlbumDetailedInfo())
    }

    override suspend fun deleteAlbum(album: AlbumDetailedInfo) = withContext(IO) {
        dao.removeAlbumData(album.toDatabaseAlbumDetailedInfo())
    }

    override suspend fun getAlbumDetailedInfo(album: AlbumShortInfo) = withContext(IO) {
        val albums = dao.getAlbumsDetailedInfo(album.id)
            .map { it.toAlbumDetailedInfo() }

        if (albums.size == 1) {
            Either.Result(albums.first())
        } else {
            Either.Error<AlbumDetailedInfo>()
        }
    }

    companion object {

        private fun DatabaseAlbumDetailedInfo.toAlbumDetailedInfo(): AlbumDetailedInfo {
            return AlbumDetailedInfo(
                AlbumShortInfo(
                    this.album.name,
                    this.album.id,
                    AlbumShortInfo.Images(
                        this.album.previewUrl,
                        this.album.coverUrl
                    ),
                    this.artist.toArtistShortInfo()
                ),
                this.tracks.map { AlbumDetailedInfo.Track(it.id, it.duration, it.name) }
            )
        }

        private fun DatabaseAlbumShortInfo.toAlbumShortInfo() =
            AlbumShortInfo(
                this.album.name,
                this.album.id,
                AlbumShortInfo.Images(
                    this.album.previewUrl,
                    this.album.coverUrl
                ),
                this.artist.toArtistShortInfo()
            )

        private fun DatabaseArtist.toArtistShortInfo() =
            ArtistShortInfo(
                this.name,
                this.id,
                ArtistShortInfo.Images(
                    this.previewUrl,
                    this.coverUrl
                )
            )

        private fun AlbumDetailedInfo.toDatabaseAlbumDetailedInfo(): DatabaseAlbumDetailedInfo {
            val artistInfo = this.shortInfo.artistShortInfo.toDatabaseArtist()
            val albumInfo = this.shortInfo.toDatabaseAlbum()

            val tracks = this.tracks.map { DatabaseTrack(it.id, albumInfo.id, it.name, it.duration) }

            return DatabaseAlbumDetailedInfo(albumInfo, artistInfo, tracks)
        }

        private fun ArtistShortInfo.toDatabaseArtist() =
            DatabaseArtist(this.id, this.name, this.images.previewUrl, this.images.coverUrl)

        private fun AlbumShortInfo.toDatabaseAlbum() =
            DatabaseAlbum(this.id, this.artistShortInfo.id, this.name, this.images.previewUrl, this.images.coverUrl)
    }
}
