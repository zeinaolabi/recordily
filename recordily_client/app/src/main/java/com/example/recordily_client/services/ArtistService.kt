package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.ArtistResponse

class ArtistService {
    suspend fun followedArtists(token: String): List<ArtistResponse>{
        return RetrofitInstance.artistAPI.followedArtists(token)
    }

    suspend fun getArtist(token: String, artist_id: String): ArtistResponse{
        return RetrofitInstance.artistAPI.getArtist(token, artist_id)
    }

    suspend fun getArtistFollowers(token: String, artist_id: String): Int{
        return RetrofitInstance.artistAPI.getArtistFollowers(token, artist_id)
    }

    suspend fun isFollowed(token: String, artist_id: String): Int{
        return RetrofitInstance.artistAPI.isFollowed(token, artist_id)
    }

    suspend fun follow(token: String, artist_id: String){
        return RetrofitInstance.artistAPI.follow(token, artist_id)
    }
}