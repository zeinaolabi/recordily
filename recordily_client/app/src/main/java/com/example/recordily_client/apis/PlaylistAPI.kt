package com.example.recordily_client.apis

import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlaylistAPI {
    @GET("get_playlists")
    suspend fun getPlaylists(@Header("Authorization") token: String): List<PlaylistResponse>

    @GET("get_playlist_songs/{playlist_id}")
    suspend fun getPlaylistSongs(@Header("Authorization") token: String, @Path("playlist_id") playlist_id: String): List<SongResponse>
}