package com.yurich.lastfmapplication.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
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

    private val viewModel by viewModel<AlbumViewModel> { parametersOf(arguments?.getParcelable<AlbumShortInfo>(ALBUM)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
