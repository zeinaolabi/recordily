package com.example.recordily_client.apis

import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface AlbumAPI {
    @GET("auth/get_album_info/{album_id}")
    suspend fun getAlbumInfo(
        @Header("Authorization") token: String,
        @Path("album_id") albumID: String
    ): AlbumResponse

    @GET("auth/get_album_songs/{album_id}")
    suspend fun getAlbumSongs(
        @Header("Authorization") token: String,
        @Path("album_id") albumID: String
    ): List<SongResponse>

    @GET("auth/unreleased_albums/{limit}")
    suspend fun getUnreleasedAlbums(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<AlbumResponse>

    @GET("auth/publish_album/{album_id}")
    suspend fun publishAlbum(
        @Header("Authorization") token: String,
        @Path("album_id") albumID: Int
    )

    @Multipart
    @POST("auth/create_album")
    suspend fun createAlbum(
        @Header("Authorization") token: String,
        @Part("name") name: String,
        @Part file: MultipartBody.Part
    )
}