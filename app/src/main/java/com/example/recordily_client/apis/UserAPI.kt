package com.example.recordily_client.apis

import com.example.recordily_client.data.UserData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAPI {
    @GET("/login")
    fun login(@Query("email") email: String, @Query("password") password: String): Call<UserData>
}