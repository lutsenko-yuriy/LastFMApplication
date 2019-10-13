package com.yurich.lastfmapplication.data.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface LastFMService {

    @GET("2.0")
    suspend fun getData(@QueryMap arguments: Map<String, String>): ResponseBody

}