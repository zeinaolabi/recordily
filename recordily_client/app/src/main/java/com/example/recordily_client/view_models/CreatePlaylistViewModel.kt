package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import com.example.recordily_client.services.PlaylistService
import okhttp3.MultipartBody

class CreatePlaylistViewModel: ViewModel() {
    private val playlistService = PlaylistService()

    suspend fun addPlaylist(token: String, name: String, image: MultipartBody.Part): Boolean {
        return try {
            playlistService.addPlaylist(token, name, image)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}