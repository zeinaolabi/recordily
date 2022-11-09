package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody

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

    suspend fun addAlbum(token: String, name: String, image: MultipartBody.Part){
        return RetrofitInstance.albumAPI.createAlbum(token, name, image)
    }

    suspend fun publishAlbum(token: String, album_id: Int){
        return RetrofitInstance.albumAPI.publishAlbum(token, album_id)
    }
}