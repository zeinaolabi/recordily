package com.example.recordily_client.apis

import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}