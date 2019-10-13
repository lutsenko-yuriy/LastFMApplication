package com.yurich.lastfmapplication.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.utils.loadImage
import kotlinx.android.synthetic.main.artist_list_item.view.*

class ArtistsAdapter(private val listener: OnArtistClickListener) : PagedListAdapter<ArtistShortInfo, ArtistsAdapter.ArtistViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_list_item, parent, false)
        val viewHolder =  ArtistViewHolder(view)

        view.setOnClickListener {
            if (viewHolder.adapterPosition != NO_POSITION) {
                listener.onArtistClick(viewHolder.item)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }


    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val artistImage = itemView.preview
        private val artistName = itemView.artist_name

        lateinit var item: ArtistShortInfo

        fun bind(newItem: ArtistShortInfo) {
            this@ArtistViewHolder.item = newItem
            artistName.text = item.name
            artistImage.loadImage(item.images.previewUrl)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<ArtistShortInfo>() {
            override fun areItemsTheSame(
                oldItem: ArtistShortInfo,
                newItem: ArtistShortInfo
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ArtistShortInfo,
                newItem: ArtistShortInfo
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnArtistClickListener {
        fun onArtistClick(artist: ArtistShortInfo)
    }
}