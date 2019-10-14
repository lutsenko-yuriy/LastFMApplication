package com.yurich.lastfmapplication.presentation.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL

import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.presentation.OnAlbumSelectedListener
import com.yurich.lastfmapplication.presentation.AlbumsAdapter
import com.yurich.lastfmapplication.presentation.main.OnSearchSelectedListener
import kotlinx.android.synthetic.main.favorites_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), AlbumsAdapter.OnAlbumClickListener {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private var listener: OnAlbumSelectedListener? = null

    private val viewModel by viewModel<FavoritesViewModel>()

    private val adapter = AlbumsAdapter(this)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnAlbumSelectedListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

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

        favorite_albums_fill?.setOnClickListener {
            (parentFragment as? OnSearchSelectedListener)?.onSearchSelected()
        }

        viewModel.albumsLiveData.observe(this, Observer {
            favorite_albums_bar?.visibility = View.GONE
            if (it.isEmpty()) {
                favorite_albums_list?.visibility = View.GONE
                favorite_albums_fill?.visibility = View.VISIBLE
            } else {
                favorite_albums_list?.visibility = View.VISIBLE
                favorite_albums_fill?.visibility = View.GONE
            }
            adapter.submitList(it)
        })
    }


    override fun onAlbumClick(album: AlbumShortInfo) {
        listener?.onAlbumSelected(album)
    }

}
