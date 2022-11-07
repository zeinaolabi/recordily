package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ArtistSongsViewModel: ViewModel() {
    private val service = ArtistService()

    private val songsResult = MutableLiveData<List<SongResponse>>()
    val songsResultLiveData : LiveData<List<SongResponse>>
        get() = songsResult

    fun getAlbums(token: String, artist_id: String, limit: Int){
        viewModelScope.launch {
            songsResult.postValue(service.getArtistSongs(token, artist_id, limit))
        }
    }
}