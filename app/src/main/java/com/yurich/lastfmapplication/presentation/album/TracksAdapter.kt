package com.yurich.lastfmapplication.presentation.album

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumDetailedInfo
import kotlinx.android.synthetic.main.track_item.view.*

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {

    private val tracks = mutableListOf<AlbumDetailedInfo.Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false))
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    fun updateTracks(newTracks: List<AlbumDetailedInfo.Track>) {
        tracks.clear()
        tracks.addAll(newTracks)

        notifyDataSetChanged()
    }

    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var track: AlbumDetailedInfo.Track
        private val title = itemView.track_title

        fun bind(track: AlbumDetailedInfo.Track) {
            this.track = track

            title.text = track.name
        }

    }
}