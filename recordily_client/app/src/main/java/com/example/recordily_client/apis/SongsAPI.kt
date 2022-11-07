package com.example.recordily_client.apis

import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface SongsAPI {
    @GET("auth/suggested_songs/{limit}")
    suspend fun suggestedSongs(@Header("Authorization") token: String, @Path("limit") limit: Int): List<SongResponse>

    @GET("auth/recently_played_songs/{limit}")
    suspend fun getRecentlyPlayedSongs(@Header("Authorization") token: String, @Path("limit") limit: Int): List<SongResponse>

    @GET("auth/top_liked_songs/{limit}")
    suspend fun getTopLikedSongs(@Header("Authorization") token: String, @Path("limit") limit: Int): List<SongResponse>

    @GET("auth/top_played_songs/{limit}")
    suspend fun getTopPlayedSongs(@Header("Authorization") token: String, @Path("limit") limit: Int): List<SongResponse>

    @GET("auth/search/{input}")
    suspend fun searchForSong(@Header("Authorization") token: String, @Path("input") input: String): SearchResponse

    @GET("auth/liked_songs")
    suspend fun getLikedSongs(@Header("Authorization") token: String): List<SongResponse>

    @GET("auth/search_liked_songs/{input}")
    suspend fun searchLikedSongs(@Header("Authorization") token: String, @Path("input") input: String): List<SongResponse>

    @Multipart
    @POST("upload_song")
    suspend fun uploadSong(
        @Part("metadata") uploadSongRequest: UploadSongRequest,
        @Part file: MultipartBody.Part
    )
}