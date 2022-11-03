package com.example.recordily_client.apis

import com.example.recordily_client.services.SongService
import com.example.recordily_client.services.UserService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.111/api/")
        .client(getLoggingHttpClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userAPI: AuthAPI by lazy {
        retrofit.create(AuthAPI::class.java)
    }

    val songApi: SongsAPI by lazy {
        retrofit.create(SongsAPI::class.java)
    }

    val authClient = UserService()
    val songClient = SongService()

    private fun getLoggingHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        return client.build()
    }
}