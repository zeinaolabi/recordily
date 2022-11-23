package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import com.example.recordily_client.services.AlbumService
import okhttp3.MultipartBody

class CreateAlbumViewModel: ViewModel() {
    private val albumService = AlbumService()

    suspend fun addAlbum(token: String, name: String, image: MultipartBody.Part): Boolean {
        return try {
            albumService.addAlbum(token, name, image)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}