package com.example.recordily_client.apis

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.44.152/api/")
        .client(getLoggingHttpClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val authAPI: AuthAPI = retrofit.create(AuthAPI::class.java)
    val userAPI: UserAPI = retrofit.create(UserAPI::class.java)
    val songAPI: SongsAPI = retrofit.create(SongsAPI::class.java)
    val playlistAPI: PlaylistAPI = retrofit.create(PlaylistAPI::class.java)
    val artistAPI: ArtistAPI = retrofit.create(ArtistAPI::class.java)
    val albumAPI: AlbumAPI = retrofit.create(AlbumAPI::class.java)
    val liveEventsAPI: LiveEventsAPI = retrofit.create(LiveEventsAPI::class.java)

    private fun getLoggingHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
       client.connectTimeout(30, TimeUnit.SECONDS)
             .writeTimeout(30, TimeUnit.SECONDS)
             .readTimeout(30, TimeUnit.SECONDS)

        return client.build()
    }
}