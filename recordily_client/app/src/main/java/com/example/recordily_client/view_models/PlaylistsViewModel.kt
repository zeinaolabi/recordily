package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.PlaylistService
import kotlinx.coroutines.launch

class PlaylistsViewModel: ViewModel() {

    private val playlistService = PlaylistService()

    private val playlistResult = MutableLiveData<List<PlaylistResponse>>()
    val playlistResultLiveData : LiveData<List<PlaylistResponse>>
        get() = playlistResult

    private val searchForPlaylistResult = MutableLiveData<List<PlaylistResponse>>()
    val searchForPlaylistResultLiveData : LiveData<List<PlaylistResponse>>
        get() = searchForPlaylistResult

    fun getPlaylists(token: String) {
        viewModelScope.launch {
            playlistResult.postValue(playlistService.getPlaylists(token))
        }
    }

    fun searchForPlaylist(token: String, input: String) {
        viewModelScope.launch {
            searchForPlaylistResult.postValue(playlistService.searchForPlaylist(token, input))
        }
    }

}