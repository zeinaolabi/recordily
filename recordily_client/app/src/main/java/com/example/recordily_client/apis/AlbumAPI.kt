package com.example.recordily_client.apis

import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AlbumAPI {
    @GET("auth/get_album_info/{album_id}")
    suspend fun getAlbumInfo(
        @Header("Authorization") token: String,
        @Path("album_id") album_id: String
    ): AlbumResponse

    @GET("auth/get_album_songs/{album_id}")
    suspend fun getAlbumSongs(
        @Header("Authorization") token: String,
        @Path("album_id") album_id: String
    ): List<SongResponse>

    @GET("auth/unreleased_albums/{limit}")
    suspend fun getUnreleasedAlbums(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<AlbumResponse>
}