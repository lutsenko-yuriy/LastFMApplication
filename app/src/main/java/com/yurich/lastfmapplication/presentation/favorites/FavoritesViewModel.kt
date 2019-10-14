package com.yurich.lastfmapplication.presentation.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsCrudInterface
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val crud: AlbumsCrudInterface
) : ViewModel() {

    val favoritesAlbumsLiveData = newPagingLiveData()

    private fun newPagingLiveData(): LiveData<PagedList<AlbumShortInfo>> {
        val dataSourceFactory = object : DataSource.Factory<Int, AlbumShortInfo>() {
            override fun create(): DataSource<Int, AlbumShortInfo> {
                return AlbumPagingDataSource()
            }
        }
        return LivePagedListBuilder<Int, AlbumShortInfo>(dataSourceFactory, DEFAULT_PAGE_SIZE)
            .setInitialLoadKey(0)
            .build()
    }

    private inner class AlbumPagingDataSource :
        PageKeyedDataSource<Int, AlbumShortInfo>() {

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, AlbumShortInfo>
        ) {
            viewModelScope.launch {
                val data = crud.getPagedAlbums(0, params.requestedLoadSize)

                callback.onResult(data, null, 1)
            }
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, AlbumShortInfo>
        ) {
            viewModelScope.launch {
                val data = crud.getPagedAlbums(params.key, params.requestedLoadSize)

                callback.onResult(data, params.key + 1)
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
        const val DEFAULT_PAGE_SIZE = 10
    }
}
