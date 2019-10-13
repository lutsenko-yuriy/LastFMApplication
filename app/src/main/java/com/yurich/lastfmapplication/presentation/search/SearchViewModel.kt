package com.yurich.lastfmapplication.presentation.search

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.yurich.lastfmapplication.data.network.LastFMServiceAdapter
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistsDataSource
import kotlinx.coroutines.launch

class SearchViewModel(
    val service: ArtistsDataSource
) : ViewModel() {

    private val pagedListsLiveData = MutableLiveData<LiveData<PagedList<ArtistShortInfo>>>()
    val liveData = Transformations.switchMap(pagedListsLiveData) { it }

    fun onQueryUpdated(query: String) {
        val dataSourceFactory = object : DataSource.Factory<Int, ArtistShortInfo>() {
            override fun create(): DataSource<Int, ArtistShortInfo> {
                return ArtistPagingDataSource(query)
            }
        }
        pagedListsLiveData.postValue(
            LivePagedListBuilder<Int, ArtistShortInfo>(dataSourceFactory, DEFAULT_PAGE_SIZE)
                .setInitialLoadKey(1)
                .build()
        )
    }

    inner class ArtistPagingDataSource(private val query: String) :
        PageKeyedDataSource<Int, ArtistShortInfo>() {

        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ArtistShortInfo>
        ) {
            viewModelScope.launch {
                val data = service.getArtistsByQuery(query, 1, params.requestedLoadSize)

                if (data is LastFMServiceAdapter.Either.Result) {
                    callback.onResult(data.result, 0, 2)
                }
            }
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ArtistShortInfo>
        ) {
            viewModelScope.launch {
                val data = service.getArtistsByQuery(query, params.key, params.requestedLoadSize)

                if (data is LastFMServiceAdapter.Either.Result) {
                    callback.onResult(data.result, params.key + 1)
                }
            }
        }

        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, ArtistShortInfo>
        ) {

        }

    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
    }
}
