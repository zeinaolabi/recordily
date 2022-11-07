package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.AlbumResponse

class AlbumService {
    suspend fun getAlbumInfo(token: String, album_id: String): AlbumResponse{
        return RetrofitInstance.albumAPI.getAlbumInfo(token, album_id)
    }
}