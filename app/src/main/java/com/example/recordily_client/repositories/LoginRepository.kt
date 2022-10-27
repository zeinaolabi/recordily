package com.example.recordily_client.repositories

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest

class LoginRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return RetrofitInstance.authClient.login(loginRequest)
    }
}