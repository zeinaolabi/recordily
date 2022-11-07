package com.example.recordily_client.apis

import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.ArtistResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AlbumAPI {
    @GET("get_album_info/{album_id}")
    suspend fun getAlbumInfo(@Header("Authorization") token: String, @Path("album_id") album_id: String): AlbumResponse

}