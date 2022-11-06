package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.services.PlaylistService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class EditPlaylistViewModel: ViewModel() {
    private val service = PlaylistService()

    private val playlistResult = MutableLiveData<PlaylistResponse>()
    val playlistResultLiveData : LiveData<PlaylistResponse>
        get() = playlistResult

    fun getPlaylist(token: String, playlist_id: String) {
        viewModelScope.launch {
            playlistResult.postValue(service.getPlaylist(token, playlist_id))
        }
    }

    suspend fun editPlaylist(token: String, id: String, name: String, image: MultipartBody.Part): Boolean {
        return try {
            service.editPlaylist(token, id, name, image)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun deletePlaylist(token: String, playlist_id: String): Boolean {
        return try {
            service.deletePlaylist(token, playlist_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}