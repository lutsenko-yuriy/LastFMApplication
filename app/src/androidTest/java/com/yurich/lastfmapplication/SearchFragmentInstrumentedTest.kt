package com.yurich.lastfmapplication

import androidx.test.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.yurich.lastfmapplication.presentation.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

// TODO
// Espresso test
// 1. Open Search
// 2. Check if any element on the screen exists
@RunWith(AndroidJUnit4::class)
class SearchFragmentInstrumentedTest {

    @get:Rule
    val activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.yurich.lastfmapplication", appContext.packageName)
    }

}
