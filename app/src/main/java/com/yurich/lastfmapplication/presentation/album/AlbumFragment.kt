package com.yurich.lastfmapplication.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.utils.loadImage
import kotlinx.android.synthetic.main.album_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AlbumFragment : Fragment() {

    companion object {

        private const val ALBUM = "album"

        fun newInstance(album: AlbumShortInfo) = AlbumFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ALBUM, album)
            }
        }
    }

    private val adapter = TracksAdapter()

    private val viewModel by viewModel<AlbumViewModel> { parametersOf(arguments?.getParcelable<AlbumShortInfo>(ALBUM)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        album_like?.setOnClickListener {
            viewModel.toggleFavorite(album_like?.isChecked == false)
        }
        viewModel.albumLiveData.observe(this, Observer {
            album_cover?.loadImage(it.shortInfo.images.coverUrl)
            album_title?.text = it.shortInfo.name

            updateTracks(it.tracks)
        })
        viewModel.albumSourceLiveData.observe(this, Observer {
            album_like?.isChecked = it
        })
        initTracksList()
    }

    private fun initTracksList() {
        album_tracks?.adapter = adapter
        album_tracks?.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        album_tracks?.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    private fun updateTracks(newTracks: List<AlbumDetailedInfo.Track>) {
        adapter.updateTracks(newTracks)
    }


}
