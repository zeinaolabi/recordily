package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse

class AlbumService {
    suspend fun getAlbumInfo(token: String, album_id: String): AlbumResponse{
        return RetrofitInstance.albumAPI.getAlbumInfo(token, album_id)
    }

    suspend fun getAlbumSongs(token: String, album_id: String): List<SongResponse>{
        return RetrofitInstance.albumAPI.getAlbumSongs(token, album_id)
    }

    suspend fun getUnreleasedAlbum(token: String, limit: Int): List<AlbumResponse>{
        return RetrofitInstance.albumAPI.getUnreleasedAlbums(token, limit)
    }
}