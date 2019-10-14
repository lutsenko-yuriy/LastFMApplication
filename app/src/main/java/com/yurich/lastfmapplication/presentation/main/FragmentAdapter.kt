package com.yurich.lastfmapplication.presentation.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yurich.lastfmapplication.R
import com.yurich.lastfmapplication.presentation.favorites.FavoritesFragment
import com.yurich.lastfmapplication.presentation.search.SearchFragment

class FragmentAdapter(context: Context?, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val items = listOf(
        FavoritesFragment.newInstance(),
        SearchFragment.newInstance()
    )

    private val titles = listOf(
        context?.getString(R.string.title_home) ?: "",
        context?.getString(R.string.title_search) ?: ""
    )
    override fun getPageTitle(position: Int) = titles[position]

    override fun getItem(position: Int) = items[position]

    override fun getCount() = items.size

}