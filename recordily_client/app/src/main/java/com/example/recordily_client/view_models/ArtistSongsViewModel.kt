package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ArtistSongsViewModel: ViewModel() {
    private val artistService = ArtistService()

    private val songsResult = MutableLiveData<List<SongResponse>>()
    val songsResultLiveData : LiveData<List<SongResponse>>
        get() = songsResult

    fun getAlbums(token: String, artistID: String, limit: Int){
        viewModelScope.launch {
            songsResult.postValue(artistService.getArtistSongs(token, artistID, limit))
        }
    }
}