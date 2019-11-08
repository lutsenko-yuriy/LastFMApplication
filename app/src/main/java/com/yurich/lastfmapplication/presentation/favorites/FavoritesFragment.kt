package com.yurich.lastfmapplication.presentation.favorites

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
import com.yurich.lastfmapplication.presentation.AlbumsAdapter
import com.yurich.lastfmapplication.presentation.main.MainFragmentDirections
import kotlinx.android.synthetic.main.favorites_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), AlbumsAdapter.OnAlbumClickListener {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private val viewModel by viewModel<FavoritesViewModel>()

    private val adapter = AlbumsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorite_albums_list?.adapter = adapter
        favorite_albums_list?.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        favorite_albums_list?.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

        viewModel.newPagingLiveData().observe(this, Observer {
            adapter.submitList(it)
        })
    }


    override fun onAlbumClick(album: AlbumShortInfo) {
        val action = MainFragmentDirections.actionToAlbumFragment(album)
        findNavController().navigate(action)
    }

}
