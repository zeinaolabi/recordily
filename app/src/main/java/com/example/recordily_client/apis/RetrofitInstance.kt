package com.example.recordily_client.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val login: UserAPI = retrofit.create(UserAPI::class.java)

}