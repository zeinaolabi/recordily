package com.example.recordily_client.apis

import com.example.recordily_client.responses.PlaylistResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface PlaylistAPI {
    @GET("get_playlists")
    suspend fun getPlaylists(@Header("Authorization") token: String): List<PlaylistResponse>
}