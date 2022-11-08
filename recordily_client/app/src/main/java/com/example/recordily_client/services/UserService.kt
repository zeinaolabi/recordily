package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.responses.RegistrationResponse
import com.example.recordily_client.responses.UserResponse
import okhttp3.MultipartBody

class UserService {
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return RetrofitInstance.authAPI.login(loginRequest)
    }

    suspend fun register(registrationRequest: RegistrationRequest): RegistrationResponse {
        return RetrofitInstance.authAPI.register(registrationRequest)
    }

    suspend fun getUserInfo(token: String): UserResponse {
        return RetrofitInstance.userAPI.getUserInfo(token)
    }

    suspend fun editProfile(token: String, name: String, bio: String, image: MultipartBody.Part?) {
        return RetrofitInstance.userAPI.editProfile(token, name, bio, image)
    }
}