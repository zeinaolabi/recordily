package com.example.recordily_client.apis

import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaylistAPI {
    @GET("get_playlists")
    suspend fun getPlaylists(@Header("Authorization") token: String): List<PlaylistResponse>

    @GET("get_playlist_songs/{playlist_id}")
    suspend fun getPlaylistSongs(@Header("Authorization") token: String, @Path("playlist_id") playlist_id: String): List<SongResponse>

    @GET("get_playlist/{playlist_id}")
    suspend fun getPlaylist(@Header("Authorization") token: String, @Path("playlist_id") playlist_id: String): PlaylistResponse

    @Multipart
    @POST("add_playlist")
    suspend fun addPlaylist(
        @Header("Authorization") token: String,
        @Part("name") name: String,
        @Part file: MultipartBody.Part
    )

    @Multipart
    @POST("edit_playlist")
    suspend fun editPlaylist(
        @Header("Authorization") token: String,
        @Part("playlist_id") playlist_id: String,
        @Part("name") name: String,
        @Part file: MultipartBody.Part
    )
}