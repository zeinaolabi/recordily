package com.example.recordily_client.apis

import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.responses.SongResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ArtistAPI {
    @GET("auth/followed_artists")
    suspend fun followedArtists(
        @Header("Authorization") token: String
    ): List<UserResponse>

    @GET("auth/get_artist_info/{artist_id}")
    suspend fun getArtist(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String
    ): UserResponse

    @GET("auth/get_artist_followers/{artist_id}")
    suspend fun getArtistFollowers(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String
    ): Int

    @GET("auth/is_followed/{artist_id}")
    suspend fun isFollowed(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String
    ): Boolean

    @GET("auth/follow/{artist_id}")
    suspend fun follow(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String
    )

    @GET("auth/unfollow/{artist_id}")
    suspend fun unfollow(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String
    )

    @GET("auth/search_followed_artist/{input}")
    suspend fun searchFollowedArtists(
        @Header("Authorization") token: String,
        @Path("input") input: String
    ): List<UserResponse>

    @GET("auth/get_artist_albums/{artist_id}/{limit}")
    suspend fun getAlbums(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String,
        @Path("limit") limit: Int
    ): List<AlbumResponse>

    @GET("auth/get_artist_songs/{artist_id}/{limit}")
    suspend fun getArtistSongs(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/get_artist_top_songs/{artist_id}/{limit}")
    suspend fun getArtistTopSongs(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String,
        @Path("limit") limit: Int
    ): List<SongResponse>

    @GET("auth/get_artist_top_albums/{artist_id}/{limit}")
    suspend fun getArtistTopAlbums(
        @Header("Authorization") token: String,
        @Path("artist_id") artistID: String,
        @Path("limit") limit: Int
    ): List<AlbumResponse>

    @GET("auth/get_top_artists/{limit}")
    suspend fun getTopArtists(
        @Header("Authorization") token: String,
        @Path("limit") limit: Int
    ): List<UserResponse>

    @GET("auth/get_views_per_month")
    suspend fun getViewsPerMonth(
        @Header("Authorization") token: String
    ): Array<Int>
}