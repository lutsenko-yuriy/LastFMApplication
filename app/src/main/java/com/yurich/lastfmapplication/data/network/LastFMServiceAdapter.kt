package com.yurich.lastfmapplication.data.network

import android.content.Context
import com.google.gson.Gson
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.data.entities.ArtistsResponseBody
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsDataSource
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource

class LastFMServiceAdapter(
    context: Context,
    private val service: LastFMService
) : ArtistsDataSource, AlbumsDataSource {

    private val apiKey = context.resources.getString(R.string.last_fm_app_key)
    private val gson = Gson()

    override suspend fun getArtistsByQuery(
        query: String,
        page: Int,
        limit: Int
    ): List<ArtistShortInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getAlbumsByArtist(
        artistShortInfo: ArtistShortInfo,
        page: Int,
        limit: Int
    ): List<AlbumShortInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTracksByAlbum(
        artist: ArtistShortInfo,
        album: AlbumShortInfo
    ): AlbumDetailedInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {

        fun ArtistsResponseBody.NetworkArtist.toArtist() =
            ArtistShortInfo(
                this.name,
                this.mbid,
                this.getImages()
            )

        private fun ArtistsResponseBody.NetworkArtist.getImages(): ArtistShortInfo.Images {
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

    }
}