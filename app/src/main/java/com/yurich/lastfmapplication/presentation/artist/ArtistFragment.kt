package com.yurich.lastfmapplication.presentation.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.presentation.AlbumsAdapter
import com.yurich.lastfmapplication.utils.loadImage
import kotlinx.android.synthetic.main.artist_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ArtistFragment : Fragment(), AlbumsAdapter.OnAlbumClickListener {

    companion object {

        private const val ARTIST = "artist"

    }

    private val adapter = AlbumsAdapter(this)

    private val viewModel by viewModel<ArtistViewModel> { parametersOf(arguments?.getParcelable<ArtistShortInfo>(ARTIST)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.artist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        artist_albums?.adapter = adapter
        artist_albums?.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        artist_albums?.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

        viewModel.artistLiveData.observe(this, Observer {
            artist_cover.loadImage(it.images.coverUrl)
            artist_name.text = it.name
        })
        viewModel.albumsLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onAlbumClick(album: AlbumShortInfo) {
        val action = ArtistFragmentDirections.actionToAlbumFragment(album)
        findNavController().navigate(action)
    }

}
