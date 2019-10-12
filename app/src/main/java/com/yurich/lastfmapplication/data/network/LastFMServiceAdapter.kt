package com.yurich.lastfmapplication.data.network

import com.google.gson.Gson
import com.yurich.lastfmapplication.data.network.entities.ArtistsResponseBody
import com.yurich.lastfmapplication.data.network.entities.TopAlbumsResponseBody
import com.yurich.lastfmapplication.data.network.entities.TracksResponseBody
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsDataSource
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource
import com.yurich.lastfmapplication.utils.fromJson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class LastFMServiceAdapter(
    private val apiKey: String,
    private val service: LastFMService
) : ArtistsDataSource, AlbumsDataSource {

    private val gson = Gson()

    override suspend fun getArtistsByQuery(
        query: String,
        page: Int,
        limit: Int
    ): Either<List<ArtistShortInfo>> = withContext(IO) {
        val response = service.getData(
            mapOf(
                ARTIST_QUERY to query,
                API_KEY to apiKey,
                PAGE to page.toString(),
                LIMIT to limit.toString()
            )
        )

        try {
            return@withContext Either.Result(
                gson.fromJson<ArtistsResponseBody>(response.string())
                    .results
                    .artistMatches
                    .artist
                    .map { it.toArtistShortInfo() }
            )
        } catch(e: Exception) {
            return@withContext Either.Error<List<ArtistShortInfo>>()
        }
    }

    override suspend fun getAlbumsByArtist(
        artist: ArtistShortInfo,
        page: Int,
        limit: Int
    ): Either<List<AlbumShortInfo>> = withContext(IO) {
        val response = service.getData(
            mapOf(
                ARTIST_QUERY to artist.name,
                API_KEY to apiKey,
                PAGE to page.toString(),
                LIMIT to limit.toString()
            )
        )

        try {
            return@withContext Either.Result(
                gson.fromJson<TopAlbumsResponseBody>(response.string())
                    .topAlbums
                    .album
                    .map { it.toAlbumShortInfo(artist) }
            )
        } catch (e: Exception) {
            return@withContext Either.Error<List<AlbumShortInfo>>()
        }

    }

    override suspend fun getTracksByAlbum(
        album: AlbumShortInfo
    ): Either<AlbumDetailedInfo> = withContext(IO) {
        val response = service.getData(
            mapOf(
                ARTIST_QUERY to album.artistShortInfo.name,
                ALBUM_QUERY to album.name,
                API_KEY to apiKey
            )
        )

        try {
            return@withContext Either.Result(
                gson.fromJson<TracksResponseBody>(response.string())
                    .album
                    .toAlbumDetailedInfo(album)
            )
        } catch(e: Exception) {
            return@withContext Either.Error<AlbumDetailedInfo>()
        }

    }


    companion object {

        // common fields
        private const val API_KEY = "api_key"
        private const val LIMIT = "limit"
        private const val PAGE = "page"

        // for artist queries
        private const val ARTIST_QUERY = "artist"

        // for albums queries
        private const val ALBUM_QUERY = "album"


        fun ArtistsResponseBody.NetworkArtist.toArtistShortInfo() =
            ArtistShortInfo(
                this.name,
                this.mbid,
                this.getArtistImages()
            )

        private fun ArtistsResponseBody.NetworkArtist.getArtistImages(): ArtistShortInfo.Images {
            var previewUrl = ""
            var coverUrl = ""

            for (img in image) {
                if (img.size == "medium") {
                    previewUrl = img.text
                }

                if (img.size == "large") {
                    coverUrl = img.text
                }
            }

            return ArtistShortInfo.Images(previewUrl, coverUrl)
        }

        private fun TopAlbumsResponseBody.Album.toAlbumShortInfo(artistShortInfo: ArtistShortInfo) =
            AlbumShortInfo(
                this.name,
                this.mbid,
                this.getAlbumImages(),
                artistShortInfo
            )

        private fun TopAlbumsResponseBody.Album.getAlbumImages(): AlbumShortInfo.Images {
            var previewUrl = ""
            var coverUrl = ""

            for (img in image) {
                if (img.size == "medium") {
                    previewUrl = img.text
                }

                if (img.size == "large") {
                    coverUrl = img.text
                }
            }

            return AlbumShortInfo.Images(previewUrl, coverUrl)
        }

        private fun TracksResponseBody.Album.toAlbumDetailedInfo(album: AlbumShortInfo) =
            AlbumDetailedInfo(
                album,
                this.tracks.track.map { AlbumDetailedInfo.Track(it.duration, it.name) }
            )
    }

    sealed class Either<T> {
        class Result<T>(val result: T) : Either<T>()
        class Error<T> : Either<T>()
    }

}
