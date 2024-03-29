package com.yurich.lastfmapplication.presentation.search

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.yurich.lastfmapplication.domain.status.Either
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchViewModel(
    val service: ArtistsDataSource
) : ViewModel() {

    val errorLiveData = MutableLiveData<Unit>()
    private val queryLiveData = MutableLiveData<String>()
    private val pagedListsLiveData = queryLiveData.map { newPagingLiveData(it) }
    val listLiveData = pagedListsLiveData.switchMap { it }

    fun onQueryUpdated(query: String) {
        queryLiveData.postValue(query)
    }

    private fun newPagingLiveData(query: String): LiveData<PagedList<ArtistShortInfo>> {
        val dataSourceFactory = object : DataSource.Factory<Int, ArtistShortInfo>() {
            override fun create(): DataSource<Int, ArtistShortInfo> {
                return ArtistPagingDataSource(query)
            }
        }
        return LivePagedListBuilder<Int, ArtistShortInfo>(dataSourceFactory, DEFAULT_PAGE_SIZE)
                .setInitialLoadKey(1)
                .build()
    }

    private inner class ArtistPagingDataSource(private val query: String) :
        PageKeyedDataSource<Int, ArtistShortInfo>() {

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ArtistShortInfo>
        ) {
            viewModelScope.launch {
                val data = service.getArtistsByQuery(query, 1, params.requestedLoadSize)

                if (data is Either.Result) {
                    callback.onResult(data.result, null, 2)
                } else {
                    errorLiveData.postValue(Unit)
                }
            }
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ArtistShortInfo>
        ) {
            viewModelScope.launch {
                val data = service.getArtistsByQuery(query, params.key, params.requestedLoadSize)

                if (data is Either.Result) {
                    callback.onResult(data.result, params.key + 1)
                } else {
                    errorLiveData.postValue(Unit)
                }
            }
        }

        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ArtistShortInfo>
        ) {

        }

        override fun invalidate() {
            super.invalidate()
            viewModelScope.cancel()
        }

    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 15
    }
}
