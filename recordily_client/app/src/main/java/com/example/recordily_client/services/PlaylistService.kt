package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.PlaylistResponse

class PlaylistService {
    suspend fun getPlaylists(token: String): List<PlaylistResponse>{
        return RetrofitInstance.playlistAPI.getPlaylists(token)
    }
}