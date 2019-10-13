package com.yurich.lastfmapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }
}
