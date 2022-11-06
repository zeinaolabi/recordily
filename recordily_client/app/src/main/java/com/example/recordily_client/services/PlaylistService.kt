package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody

class PlaylistService {
    suspend fun getPlaylists(token: String): List<PlaylistResponse>{
        return RetrofitInstance.playlistAPI.getPlaylists(token)
    }

    suspend fun getPlaylistSongs(token: String, playlist_id: String): List<SongResponse>{
        return RetrofitInstance.playlistAPI.getPlaylistSongs(token, playlist_id)
    }

    suspend fun addPlaylist(token: String, name: String, image: MultipartBody.Part){
        return RetrofitInstance.playlistAPI.addPlaylist(token, name, image)
    }

    suspend fun getPlaylist(token: String, playlist_id: String): PlaylistResponse {
        return RetrofitInstance.playlistAPI.getPlaylist(token, playlist_id)
    }
}