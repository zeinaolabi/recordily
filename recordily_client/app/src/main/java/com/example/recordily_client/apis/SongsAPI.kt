package com.example.recordily_client.apis

import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SongsAPI {
    @GET("suggested_songs/{limit}")
    suspend fun suggestedSongs(@Path("limit") limit: Int): List<SongResponse>

}