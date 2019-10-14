package com.yurich.lastfmapplication.presentation.album

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.albums.AlbumsCrudInterface
import com.yurich.lastfmapplication.domain.status.Either
import kotlinx.coroutines.launch

class AlbumViewModel(
    private val album: AlbumShortInfo,
    private val singleAlbumsDataSource: SingleAlbumDataSource,
    private val crud: AlbumsCrudInterface
) : ViewModel() {

    val albumLiveData = MutableLiveData<AlbumDetailedInfo>()
    val albumSourceLiveData = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            val albumStatus = singleAlbumsDataSource.getAlbumDetailedInfo(album)
            if (albumStatus.result is Either.Result) {
                albumLiveData.postValue(albumStatus.result.result)
            }
            albumSourceLiveData.postValue(albumStatus.source == SingleAlbumDataSource.Source.LOCAL_STORAGE)
        }
    }

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            albumLiveData.value?.run {
                if (isFavorite) {
                    crud.putAlbum(this)
                } else {
                    crud.deleteAlbum(this)
                }
                albumSourceLiveData.postValue(isFavorite)
            }
        }
    }
}
