package com.yurich.lastfmapplication.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), ArtistsAdapter.OnArtistClickListener {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val viewModel: SearchViewModel by viewModel()

    private val adapter = ArtistsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initSearch()
    }

    private fun initSearch() {
        search_text_field?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                submitQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    search_button?.visibility = View.GONE
                } else {
                    search_button?.visibility = View.VISIBLE
                }
                return true
            }

        })
        search_button?.setOnClickListener {
            submitQuery(search_text_field?.query?.toString())
        }
    }

    private fun initList() {
        search_result?.adapter = adapter
        viewModel.errorLiveData.observe(this, Observer {
            search_error_message?.visibility = View.VISIBLE
        })
        viewModel.listLiveData.observe(this, Observer {
            search_error_message?.visibility = View.GONE
            adapter.submitList(it)
        })
    }

    private fun submitQuery(query: String?) {
        if (!query.isNullOrBlank()) {
            viewModel.onQueryUpdated(query)
        }
    }

    override fun onArtistClick(artist: ArtistShortInfo) {

    }
}
