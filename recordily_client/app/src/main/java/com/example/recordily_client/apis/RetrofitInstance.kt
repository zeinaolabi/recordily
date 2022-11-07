package com.example.recordily_client.apis

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.104/api/")
        .client(getLoggingHttpClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val userAPI: AuthAPI = retrofit.create(AuthAPI::class.java)
    val songAPI: SongsAPI = retrofit.create(SongsAPI::class.java)
    val playlistAPI: PlaylistAPI = retrofit.create(PlaylistAPI::class.java)
    val artistAPI: ArtistAPI = retrofit.create(ArtistAPI::class.java)

    private fun getLoggingHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        return client.build()
    }
}