package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class SongsStatsViewModel: ViewModel() {
    val userService = UserService()

    private val songsResult = MutableLiveData<List<SongResponse>>()
    val songsResultLiveData: LiveData<List<SongResponse>>
        get() = songsResult

    private val searchResult = MutableLiveData<List<SongResponse>>()
    val searchResultLiveData: LiveData<List<SongResponse>>
        get() = searchResult

    fun getUserSongs(token: String){
        viewModelScope.launch{
            songsResult.postValue(userService.getUserSongs(token))
        }
    }

    fun searchReleasedSongs(token: String, input: String){
        viewModelScope.launch{
            searchResult.postValue(userService.searchReleasedSongs(token, input))
        }
    }
}