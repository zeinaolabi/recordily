package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody

class AlbumService {
    suspend fun getAlbumInfo(token: String, albumID: String): AlbumResponse{
        return RetrofitInstance.albumAPI.getAlbumInfo(token, albumID)
    }

    suspend fun getAlbumSongs(token: String, albumID: String): List<SongResponse>{
        return RetrofitInstance.albumAPI.getAlbumSongs(token, albumID)
    }

    suspend fun getUnreleasedAlbum(token: String, limit: Int): List<AlbumResponse>{
        return RetrofitInstance.albumAPI.getUnreleasedAlbums(token, limit)
    }

    suspend fun addAlbum(token: String, name: String, image: MultipartBody.Part){
        return RetrofitInstance.albumAPI.createAlbum(token, name, image)
    }

    suspend fun publishAlbum(token: String, albumID: Int){
        return RetrofitInstance.albumAPI.publishAlbum(token, albumID)
    }
}