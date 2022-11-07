package com.example.recordily_client.apis

import com.example.recordily_client.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserAPI {
    @GET("auth/get_user_info")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserResponse
}