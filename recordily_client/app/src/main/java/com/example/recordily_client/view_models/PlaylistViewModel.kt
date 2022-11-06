package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.PlaylistService
import kotlinx.coroutines.launch

class PlaylistViewModel: ViewModel() {
    private val playlistService = PlaylistService()

    private val playlistSongsResult = MutableLiveData<List<SongResponse>>()
    val playlistSongsResultLiveData : LiveData<List<SongResponse>>
        get() = playlistSongsResult

    fun getPlaylistSongs(token: String, playlist_id: String){
        viewModelScope.launch {
            playlistSongsResult.postValue(playlistService.getPlaylistSongs(token, playlist_id))
        }
    }
}