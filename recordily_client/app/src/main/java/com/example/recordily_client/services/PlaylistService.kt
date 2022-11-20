package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody

class PlaylistService {
    suspend fun getPlaylists(token: String): List<PlaylistResponse>{
        return RetrofitInstance.playlistAPI.getPlaylists(token)
    }

    suspend fun getLimitedPlaylists(token: String, limit: Int): List<PlaylistResponse>{
        return RetrofitInstance.playlistAPI.getLimitedPlaylists(token, limit)
    }

    suspend fun getPlaylistSongs(token: String, playlistID: String): List<SongResponse>{
        return RetrofitInstance.playlistAPI.getPlaylistSongs(token, playlistID)
    }

    suspend fun getPlaylist(token: String, playlistID: String): PlaylistResponse {
        return RetrofitInstance.playlistAPI.getPlaylist(token, playlistID)
    }

    suspend fun deletePlaylist(token: String, playlistID: String) {
        return RetrofitInstance.playlistAPI.deletePlaylist(token, playlistID)
    }

    suspend fun addPlaylist(token: String, name: String, image: MultipartBody.Part){
        return RetrofitInstance.playlistAPI.addPlaylist(token, name, image)
    }

    suspend fun editPlaylist(token: String, playlistID: String, name: String, image: MultipartBody.Part?){
        return RetrofitInstance.playlistAPI.editPlaylist(token, playlistID, name, image)
    }

    suspend fun searchForPlaylist(token: String, input: String): List<PlaylistResponse> {
        return RetrofitInstance.playlistAPI.searchForPlaylist(token, input)
    }

    suspend fun addToPlaylist(token: String, playlistID: Int, songID: Int) {
        return RetrofitInstance.playlistAPI.addToPlaylist(token, playlistID, songID)
    }

    suspend fun removeFromPlaylist(token: String, playlistID: Int, songID: Int) {
        return RetrofitInstance.playlistAPI.removeFromPlaylist(token, playlistID, songID)
    }
}