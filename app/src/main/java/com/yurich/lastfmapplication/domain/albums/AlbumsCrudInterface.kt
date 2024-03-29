package com.yurich.lastfmapplication.domain.albums

interface AlbumsCrudInterface {

    suspend fun getPagedAlbums(page: Int, limit: Int): List<AlbumShortInfo>

    suspend fun putAlbum(album: AlbumDetailedInfo)

    suspend fun deleteAlbum(album: AlbumDetailedInfo)

}