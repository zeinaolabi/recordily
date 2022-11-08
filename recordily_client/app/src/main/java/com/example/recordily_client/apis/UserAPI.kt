package com.example.recordily_client.apis

import com.example.recordily_client.responses.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserAPI {
    @GET("auth/get_user_info")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserResponse

    @Multipart
    @POST("auth/edit_profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Part("name") name: String,
        @Part("biography") bio: String,
        @Part file: MultipartBody.Part?
    )
}