package com.yurich.lastfmapplication

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

// TODO
// Espresso test
// 1. Open Search
// 2. Check if any element on the screen exists
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.yurich.lastfmapplication", appContext.packageName)
    }
}
