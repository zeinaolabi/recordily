package com.example.recordily_client.apis

import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface PlaylistAPI {
    @GET("auth/get_playlists")
    suspend fun getPlaylists(@Header("Authorization") token: String): List<PlaylistResponse>

    @GET("auth/get_playlist_songs/{playlist_id}")
    suspend fun getPlaylistSongs(@Header("Authorization") token: String, @Path("playlist_id") playlist_id: String): List<SongResponse>

    @GET("auth/get_playlist/{playlist_id}")
    suspend fun getPlaylist(@Header("Authorization") token: String, @Path("playlist_id") playlist_id: String): PlaylistResponse

    @GET("auth/delete_playlist/{playlist_id}")
    suspend fun deletePlaylist(@Header("Authorization") token: String, @Path("playlist_id") playlist_id: String)

    @GET("auth/search_playlists/{input}")
    suspend fun searchForPlaylist(@Header("Authorization") token: String, @Path("input") input: String): List<PlaylistResponse>

    @Multipart
    @POST("auth/add_playlist")
    suspend fun addPlaylist(
        @Header("Authorization") token: String,
        @Part("name") name: String,
        @Part file: MultipartBody.Part
    )

    @Multipart
    @POST("auth/edit_playlist")
    suspend fun editPlaylist(
        @Header("Authorization") token: String,
        @Part("playlist_id") playlist_id: String,
        @Part("name") name: String,
        @Part file: MultipartBody.Part?
    )
}