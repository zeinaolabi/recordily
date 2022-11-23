package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.services.AlbumService
import kotlinx.coroutines.launch

class UnreleasedAlbumsViewModel: ViewModel() {
    private val albumService = AlbumService()

    private val unreleasedAlbumsResult = MutableLiveData<List<AlbumResponse>>()
    val unreleasedAlbumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = unreleasedAlbumsResult

    fun getUnreleasedSongs(token: String, limit: Int){
        viewModelScope.launch {
            unreleasedAlbumsResult.postValue(albumService.getUnreleasedAlbum(token, limit))
        }
    }

    suspend fun publishAlbum(token: String, albumID: Int): Boolean {
        return try {
            albumService.publishAlbum(token, albumID)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}