package com.example.recordily_client.apis

import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface SongsAPI {
    @GET("auth/suggested_songs/{limit}")
    suspend fun suggestedSongs(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/recently_played_songs/{limit}")
    suspend fun getRecentlyPlayedSongs(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/top_liked_songs/{limit}")
    suspend fun getTopLikedSongs(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/top_played_songs/{limit}")
    suspend fun getTopPlayedSongs(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/search/{input}")
    suspend fun searchForSong(
        @Header("Authorization") token: String,
        @Path("input") input: String
    ): SearchResponse

    @GET("auth/liked_songs")
    suspend fun getLikedSongs(
        @Header("Authorization") token: String
    ): List<SongResponse>

    @GET("auth/search_liked_songs/{input}")
    suspend fun searchLikedSongs(
        @Header("Authorization") token: String,
        @Path("input") input: String
    ): List<SongResponse>

    @GET("auth/unreleased_songs/{limit}")
    suspend fun getUnreleasedSongs(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/delete_song_from_album/{song_id}")
    suspend fun deleteFromAlbum(
        @Header("Authorization") token: String,
        @Path("song_id") song_id: Int
    )

    @GET("auth/publish_song/{song_id}")
    suspend fun publishSong(
        @Header("Authorization") token: String,
        @Path("song_id") song_id: Int
    )

    @GET("auth/is_liked/{song_id}")
    suspend fun isLiked(
        @Header("Authorization") token: String,
        @Path("song_id") song_id: Int
    ): Boolean

    @GET("auth/like/{song_id}")
    suspend fun likeSong(
        @Header("Authorization") token: String,
        @Path("song_id") song_id: Int
    )

    @GET("auth/unlike/{song_id}")
    suspend fun unlikeSong(
        @Header("Authorization") token: String,
        @Path("song_id") song_id: Int
    )

    @Multipart
    @POST("auth/upload_song")
    suspend fun uploadSong(
        @Header("Authorization") token: String,
        @Part("metadata") uploadSongRequest: UploadSongRequest,
        @Part song: MultipartBody.Part,
        @Part picture: MultipartBody.Part
    )
}