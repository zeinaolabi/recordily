package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.SimpleResponse
import com.example.recordily_client.apis.UserAPI
import com.example.recordily_client.responses.LoginResponse
import com.example.recordily_client.requests.LoginRequest
import retrofit2.Response
import java.lang.Exception

class UserService{
    suspend fun login(loginRequest: LoginRequest): SimpleResponse<LoginResponse>{
        return safeApiCall { RetrofitInstance.userAPI.login(loginRequest) }
    }

    private inline fun<T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try{
            SimpleResponse.success(apiCall.invoke())
        }
        catch (e: Exception){
            SimpleResponse.failure(e)
        }
    }

}