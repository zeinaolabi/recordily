package com.example.recordily_client.services

import com.example.recordily_client.apis.UserAPI
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import retrofit2.Response

class UserService( private val userAPI: UserAPI) {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>{
        return userAPI.login(loginRequest)
    }

}