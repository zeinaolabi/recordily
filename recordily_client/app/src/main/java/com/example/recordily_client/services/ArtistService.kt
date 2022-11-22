package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.responses.SongResponse

class ArtistService {
    suspend fun followedArtists(token: String): List<UserResponse>{
        return RetrofitInstance.artistAPI.followedArtists(token)
    }

    suspend fun getArtist(token: String, artistID: String): UserResponse{
        return RetrofitInstance.artistAPI.getArtist(token, artistID)
    }

    suspend fun getArtistFollowers(token: String, artistID: String): Int{
        return RetrofitInstance.artistAPI.getArtistFollowers(token, artistID)
    }

    suspend fun isFollowed(token: String, artistID: String): Boolean{
        return RetrofitInstance.artistAPI.isFollowed(token, artistID)
    }

    suspend fun follow(token: String, artistID: String){
        return RetrofitInstance.artistAPI.follow(token, artistID)
    }

    suspend fun unfollow(token: String, artistID: String){
        return RetrofitInstance.artistAPI.unfollow(token, artistID)
    }

    suspend fun getAlbums(token: String, artistID: String, limit: Int): List<AlbumResponse>{
        return RetrofitInstance.artistAPI.getAlbums(token, artistID, limit)
    }

    suspend fun getArtistTopSongs(token: String, artistID: String, limit: Int): List<SongResponse>{
        return RetrofitInstance.artistAPI.getArtistTopSongs(token, artistID, limit)
    }

    suspend fun getArtistSongs(token: String, artistID: String, limit: Int): List<SongResponse>{
        return RetrofitInstance.artistAPI.getArtistSongs(token, artistID, limit)
    }

    suspend fun searchFollowedArtist(token: String, input: String): List<UserResponse>{
        return RetrofitInstance.artistAPI.searchFollowedArtists(token, input)
    }

    suspend fun getViewsPerMonth(token: String): Array<Int>{
        return RetrofitInstance.artistAPI.getViewsPerMonth(token)
    }

    suspend fun getArtistTopAlbums(token: String, artistID: String, limit: Int): List<AlbumResponse>{
        return RetrofitInstance.artistAPI.getArtistTopAlbums(token, artistID, limit)
    }

    suspend fun getTopArtists(token: String, limit: Int): List<UserResponse>{
        return RetrofitInstance.artistAPI.getTopArtists(token, limit)
    }
}