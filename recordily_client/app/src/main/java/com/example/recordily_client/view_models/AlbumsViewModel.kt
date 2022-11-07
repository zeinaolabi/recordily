package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class AlbumsViewModel: ViewModel() {
    private val service = ArtistService()

    private val albumsResult = MutableLiveData<List<AlbumResponse>>()
    val albumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = albumsResult

    fun getAlbums(token: String, artist_id: String, limit: Int){
        viewModelScope.launch {
            albumsResult.postValue(service.getAlbums(token, artist_id, limit))
        }
    }
}