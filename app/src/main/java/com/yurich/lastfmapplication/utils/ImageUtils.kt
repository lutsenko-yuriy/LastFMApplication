package com.yurich.lastfmapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .fitCenter()
        .into(this)
}