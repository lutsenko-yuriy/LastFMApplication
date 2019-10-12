package com.yurich.lastfmapplication.utils

import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(text: String): T = this.fromJson<T>(text, T::class.java)