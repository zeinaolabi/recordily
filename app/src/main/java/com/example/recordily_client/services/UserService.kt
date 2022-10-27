package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.responses.RegistrationResponse

class UserService{
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return RetrofitInstance.userAPI.login(loginRequest)
    }

    suspend fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        return RetrofitInstance.userAPI.register(registrationRequest)
    }
}