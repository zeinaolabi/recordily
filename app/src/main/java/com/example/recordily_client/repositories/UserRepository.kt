package com.example.recordily_client.repositories

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest

class UserRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return RetrofitInstance.apiClient.login(loginRequest)
    }
}