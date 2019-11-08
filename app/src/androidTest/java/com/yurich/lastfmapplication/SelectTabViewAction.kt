package com.yurich.lastfmapplication

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class SelectTabViewAction(private val text: String) : ViewAction {

    override fun getDescription(): String = "with tab captioned as \"$text\""

    override fun getConstraints(): Matcher<View> =
        allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

    override fun perform(uiController: UiController?, view: View?) {
        val tabLayout = view as TabLayout
        var tab: TabLayout.Tab? = null
        for (i in 0 until tabLayout.tabCount) {
            val currentTab = tabLayout.getTabAt(i)
            if (currentTab?.text == text) {
                if (tab != null) {
                    throw PerformException.Builder()
                        .withCause(Throwable("Several tabs with the same text: $text"))
                        .build()
                } else {
                    tab = currentTab
                }
            }
        }

        if (tab == null) {
            throw PerformException.Builder()
                .withCause(Throwable("Several tabs with the same text: $text"))
                .build()
        }

        tab.select()
    }

}