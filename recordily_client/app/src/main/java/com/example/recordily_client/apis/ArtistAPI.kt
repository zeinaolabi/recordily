package com.example.recordily_client.apis

import com.example.recordily_client.responses.ArtistResponse
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ArtistAPI {
    @GET("followed_artists")
    suspend fun followedArtists(@Header("Authorization") token: String): List<ArtistResponse>
}