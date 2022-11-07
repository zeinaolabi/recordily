package com.example.recordily_client.apis

import com.example.recordily_client.responses.ArtistResponse
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ArtistAPI {
    @GET("followed_artists")
    suspend fun followedArtists(@Header("Authorization") token: String): List<ArtistResponse>

    @GET("get_artist_info/{artist_id}")
    suspend fun getArtist(@Header("Authorization") token: String, @Path("artist_id") artist_id: String): ArtistResponse

    @GET("get_artist_followers/{artist_id}")
    suspend fun getArtistFollowers(@Header("Authorization") token: String, @Path("artist_id") artist_id: String): Int

    @GET("is_followed/{artist_id}")
    suspend fun isFollowed(@Header("Authorization") token: String, @Path("artist_id") artist_id: String): Boolean

    @GET("follow/{artist_id}")
    suspend fun follow(@Header("Authorization") token: String, @Path("artist_id") artist_id: String)

    @GET("unfollow/{artist_id}")
    suspend fun unfollow(@Header("Authorization") token: String, @Path("artist_id") artist_id: String)
}