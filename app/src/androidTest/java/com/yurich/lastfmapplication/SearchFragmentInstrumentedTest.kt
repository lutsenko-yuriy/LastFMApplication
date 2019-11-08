package com.yurich.lastfmapplication

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.yurich.lastfmapplication.presentation.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// TODO
// Espresso test
// 1. Open Search
// 2. Check if any element on the screen exists
@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchFragmentInstrumentedTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    lateinit var activity: Activity

    @Before
    fun getActivity() {
        activity = activityRule.activity
    }

    @Test
    fun searchFragmentOpensWell() {
        Thread.sleep(1000)
        onView(withId(R.id.tabs))
            .perform(SelectTabViewAction(activity.getString(R.string.title_search)))

        onView(withId(R.id.search_text_field))
            .check(matches(isDisplayed()))
    }


}
