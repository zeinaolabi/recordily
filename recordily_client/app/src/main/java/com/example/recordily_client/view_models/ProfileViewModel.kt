package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.PlaylistService
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val service = UserService()
    private val playlistService = PlaylistService()

    private val userInfoResult = MutableLiveData<UserResponse>()
    val userInfoResultLiveData: LiveData<UserResponse>
        get() = userInfoResult

    private val topSongsResult = MutableLiveData<List<SongResponse>>()
    val topSongsResultLiveData: LiveData<List<SongResponse>>
        get() = topSongsResult

    private val recentlyPlayedResult = MutableLiveData<List<SongResponse>>()
    val recentlyPlayedResultLiveData: LiveData<List<SongResponse>>
        get() = recentlyPlayedResult

    private val playlistsResult = MutableLiveData<List<PlaylistResponse>>()
    val playlistsResultLiveData: LiveData<List<PlaylistResponse>>
        get() = playlistsResult

    fun getInfo(token: String){
        viewModelScope.launch {
            userInfoResult.postValue(service.getInfo(token))
        }
    }

    fun getTopSongs(token: String, limit: Int){
        viewModelScope.launch {
            topSongsResult.postValue(service.getTopSongs(token, limit))
        }
    }

    fun getRecentlyPlayed(token: String, limit: Int){
        viewModelScope.launch {
            recentlyPlayedResult.postValue(service.getTopSongs(token, limit))
        }
    }

    fun getPlaylists(token: String, limit: Int){
        viewModelScope.launch {
            playlistsResult.postValue(playlistService.getLimitedPlaylists(token, limit))
        }
    }
}