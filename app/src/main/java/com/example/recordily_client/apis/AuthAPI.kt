package com.example.recordily_client.apis

import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.responses.RegistrationResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body registrationRequest: RegistrationRequest): RegistrationResponse
}