package com.yurich.lastfmapplication.presentation.artist

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.yurich.lastfmapplication.data.network.LastFMServiceAdapter
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ArtistViewModel(
    artist: ArtistShortInfo,
    private val service: ArtistsDataSource
) : ViewModel() {

    val artistLiveData = MutableLiveData(artist)

    val albumsLiveData = artistLiveData
        .switchMap { newPagingLiveData(it) }

    val errorLiveData = MutableLiveData<Boolean>()

    private fun newPagingLiveData(artist: ArtistShortInfo): LiveData<PagedList<AlbumShortInfo>> {
        val dataSourceFactory = object : DataSource.Factory<Int, AlbumShortInfo>() {
            override fun create(): DataSource<Int, AlbumShortInfo> {
                return AlbumPagingDataSource(artist)
            }
        }
        return LivePagedListBuilder<Int, AlbumShortInfo>(dataSourceFactory, DEFAULT_PAGE_SIZE)
            .setInitialLoadKey(1)
            .build()
    }

    private inner class AlbumPagingDataSource(val artist: ArtistShortInfo) :
        PageKeyedDataSource<Int, AlbumShortInfo>() {

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, AlbumShortInfo>
        ) {
            viewModelScope.launch {
                val data = service.getAlbumsByArtist(artist, 1, params.requestedLoadSize)

                if (data is LastFMServiceAdapter.Either.Result) {
                    callback.onResult(data.result, null, 2)
                } else {
                    errorLiveData.postValue(true)
                }
            }
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, AlbumShortInfo>
        ) {
            viewModelScope.launch {
                val data = service.getAlbumsByArtist(artist, params.key, params.requestedLoadSize)

                if (data is LastFMServiceAdapter.Either.Result) {
                    callback.onResult(data.result, params.key + 1)
                } else {
                    errorLiveData.postValue(true)
                }
            }
        }

        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, AlbumShortInfo>
        ) {

        }

        override fun invalidate() {
            super.invalidate()
            viewModelScope.cancel()
        }

    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
    }
}
