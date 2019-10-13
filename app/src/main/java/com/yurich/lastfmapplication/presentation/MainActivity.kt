package com.yurich.lastfmapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.domain.albums.AlbumShortInfo
import com.yurich.lastfmapplication.domain.artists.ArtistShortInfo
import com.yurich.lastfmapplication.presentation.album.AlbumFragment
import com.yurich.lastfmapplication.presentation.artist.ArtistFragment
import com.yurich.lastfmapplication.presentation.main.MainFragment
import com.yurich.lastfmapplication.presentation.search.SearchFragment

class MainActivity : AppCompatActivity(),
        SearchFragment.OnArtistSelectedListener,
        ArtistFragment.OnAlbumSelectedListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }

    override fun onArtistSelected(artist: ArtistShortInfo) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ArtistFragment.newInstance(artist))
            .addToBackStack(null)
            .commit()
    }

    override fun onAlbumSelected(album: AlbumShortInfo) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AlbumFragment.newInstance(album))
            .addToBackStack(null)
            .commit()
    }


}
