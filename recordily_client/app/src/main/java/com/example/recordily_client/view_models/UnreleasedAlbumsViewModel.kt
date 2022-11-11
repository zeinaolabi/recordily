package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.services.AlbumService
import kotlinx.coroutines.launch

class UnreleasedAlbumsViewModel: ViewModel() {
    private val service = AlbumService()

    private val unreleasedAlbumsResult = MutableLiveData<List<AlbumResponse>>()
    val unreleasedAlbumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = unreleasedAlbumsResult

    fun getUnreleasedSongs(token: String, limit: Int){
        viewModelScope.launch {
            unreleasedAlbumsResult.postValue(service.getUnreleasedAlbum(token, limit))
        }
    }

    suspend fun publishAlbum(token: String, album_id: Int): Boolean {
        return try {
            service.publishAlbum(token, album_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}