package com.example.recordily_client.apis

import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SongsAPI {
    @GET("top_played_songs")
    suspend fun topSongs(): List<SongResponse>

    @POST("upload_song")
    suspend fun uploadSong(@Body uploadSongRequest: UploadSongRequest): Boolean
}