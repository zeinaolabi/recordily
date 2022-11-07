package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.ArtistResponse

class ArtistService {
    suspend fun followedArtists(token: String): List<ArtistResponse>{
        return RetrofitInstance.artistAPI.followedArtists(token)
    }
}