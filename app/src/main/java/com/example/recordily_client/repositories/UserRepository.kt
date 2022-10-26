package com.example.recordily_client.repositories

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest

class UserRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponse? {
        val request = RetrofitInstance.apiClient.login(loginRequest)

        if(request.isSuccessful){
            return request.body()!!
        }

        return null
    }
}