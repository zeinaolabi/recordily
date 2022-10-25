package com.example.recordily_client.apis

import com.example.recordily_client.data.UserData
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {
    @GET("/login")
    suspend fun login() : Response<UserData>
}