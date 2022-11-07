package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.services.AlbumService
import kotlinx.coroutines.launch

class AlbumViewModel: ViewModel() {
    private val service = AlbumService()

    private val albumInfoResult = MutableLiveData<AlbumResponse>()
    val albumInfoResultLiveData : LiveData<AlbumResponse>
        get() = albumInfoResult

    fun getAlbumInfo(token: String, artist_id: String){
        viewModelScope.launch {
            albumInfoResult.postValue(service.getAlbumInfo(token, artist_id))
        }
    }
}