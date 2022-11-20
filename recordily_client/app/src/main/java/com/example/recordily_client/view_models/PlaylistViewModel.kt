package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.PlaylistService
import kotlinx.coroutines.launch

class PlaylistViewModel: ViewModel() {
    private val playlistService = PlaylistService()

    private val playlistSongsResult = MutableLiveData<List<SongResponse>>()
    val playlistSongsResultLiveData : LiveData<List<SongResponse>>
        get() = playlistSongsResult

    private val playlistResult = MutableLiveData<PlaylistResponse>()
    val playlistResultLiveData : LiveData<PlaylistResponse>
        get() = playlistResult

    fun getPlaylistSongs(token: String, playlistID: String){
        viewModelScope.launch {
            playlistSongsResult.postValue(playlistService.getPlaylistSongs(token, playlistID))
        }
    }

    fun getPlaylist(token: String, playlistID: String){
        viewModelScope.launch {
            playlistResult.postValue(playlistService.getPlaylist(token, playlistID))
        }
    }
}