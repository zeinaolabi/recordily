package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.AlbumService
import kotlinx.coroutines.launch

class UnreleasedAlbumViewModel: ViewModel() {
    private val service = AlbumService()

    private val albumInfoResult = MutableLiveData<AlbumResponse>()
    val albumInfoResultLiveData : LiveData<AlbumResponse>
        get() = albumInfoResult

    private val albumSongsResult = MutableLiveData<List<SongResponse>>()
    val albumSongsResultLiveData : LiveData<List<SongResponse>>
        get() = albumSongsResult

    fun getAlbumInfo(token: String, artist_id: String){
        viewModelScope.launch {
            albumInfoResult.postValue(service.getAlbumInfo(token, artist_id))
        }
    }

    fun getAlbumSongs(token: String, artist_id: String){
        viewModelScope.launch {
            albumSongsResult.postValue(service.getAlbumSongs(token, artist_id))
        }
    }
}