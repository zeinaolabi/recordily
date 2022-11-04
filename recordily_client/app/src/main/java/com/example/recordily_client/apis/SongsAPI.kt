package com.example.recordily_client.apis

import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.*

interface SongsAPI {
    @GET("suggested_songs/{limit}")
    suspend fun suggestedSongs(@Path("limit") limit: Int): List<SongResponse>

    @GET("recently_played_songs/{limit}")
    suspend fun getRecentlyPlayedSongs(@Path("limit") limit: Int): List<SongResponse>

    @GET("top_liked_songs/{limit}")
    suspend fun getTopLikedSongs(@Path("limit") limit: Int): List<SongResponse>

    @GET("top_played_songs/{limit}")
    suspend fun getTopPlayedSongs(@Path("limit") limit: Int): List<SongResponse>

    @GET("search/{input}")
    suspend fun searchForSong(@Path("input") input: String): SearchResponse

    @GET("liked_songs")
    suspend fun getLikedSongs(@Header("Authorization") token: String): List<SongResponse>

    @POST("upload_song")
    suspend fun uploadSong(@Body uploadSongRequest: UploadSongRequest): Boolean
}