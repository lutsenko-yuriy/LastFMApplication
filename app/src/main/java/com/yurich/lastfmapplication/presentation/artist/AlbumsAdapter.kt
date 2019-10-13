package com.yurich.lastfmapplication.presentation.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.utils.loadImage
import kotlinx.android.synthetic.main.album_list_item.view.*

class AlbumsAdapter(private val listener: OnAlbumClickListener) : PagedListAdapter<AlbumShortInfo, AlbumsAdapter.AlbumViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_list_item, parent, false)
        val viewHolder =  AlbumViewHolder(view)

        view.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                listener.onAlbumClick(viewHolder.item)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }


    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val albumImage = itemView.album_preview
        private val albumName = itemView.album_title
        private val albumAuthor = itemView.album_author

        lateinit var item: AlbumShortInfo

        fun bind(newItem: AlbumShortInfo) {
            this@AlbumViewHolder.item = newItem
            albumName.text = item.name
            albumAuthor.text = item.artistShortInfo.name
            albumImage.loadImage(item.images.previewUrl)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<AlbumShortInfo>() {
            override fun areItemsTheSame(
                oldItem: AlbumShortInfo,
                newItem: AlbumShortInfo
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: AlbumShortInfo,
                newItem: AlbumShortInfo
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnAlbumClickListener {
        fun onAlbumClick(album: AlbumShortInfo)
    }
}