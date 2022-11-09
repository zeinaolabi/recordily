package com.example.recordily_client.apis

import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.responses.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserAPI {
    @GET("auth/get_user_info")
    suspend fun getInfo(@Header("Authorization") token: String): UserResponse

    @GET("auth/get_user_top_songs/{limit}")
    suspend fun getTopSongs(@Header("Authorization") token: String, @Path("limit") limit: Int): List<SongResponse>

    @GET("auth/recently_played_songs/{limit}")
    suspend fun getRecentlyPlayed(@Header("Authorization") token: String, @Path("limit") limit: Int): List<SongResponse>

    @Multipart
    @POST("auth/edit_profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Part("name") name: String,
        @Part("biography") bio: String,
        @Part file: MultipartBody.Part?
    )
}