package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.SimpleResponse
import com.example.recordily_client.apis.UserAPI
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import retrofit2.Response
import java.lang.Exception

class UserService{
    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return RetrofitInstance.userAPI.login(loginRequest)
    }

}