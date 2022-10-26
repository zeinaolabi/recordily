package com.example.recordily_client.apis

import com.example.recordily_client.services.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.102/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userAPI: UserAPI by lazy {
        retrofit.create(UserAPI::class.java)
    }

    val apiClient = UserService(userAPI);

}