package com.example.recordily_client.view_models

import androidx.lifecycle.*
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class LandingPageViewModel: ViewModel(){

    private val songService = SongService()

    private val topPlayedResult = MutableLiveData<List<SongResponse>>()
    val topPlayedResultLiveData : LiveData<List<SongResponse>>
        get() = topPlayedResult

    private val topLikedResult = MutableLiveData<List<SongResponse>>()
    val topLikedResultLiveData : LiveData<List<SongResponse>>
        get() = topLikedResult

    private val suggestedResult = MutableLiveData<List<SongResponse>>()
    val suggestedResultLiveData : LiveData<List<SongResponse>>
        get() = suggestedResult

    private val recentlyPlayedResult = MutableLiveData<List<SongResponse>>()
    val recentlyPlayedResultLiveData : LiveData<List<SongResponse>>
        get() = recentlyPlayedResult

    fun getTopPlayed(token: String, limit: Int) {
        viewModelScope.launch {
            topPlayedResult.postValue(songService.getTopPlayed(token, limit))
        }
    }

    fun getTopLiked(token: String, limit: Int) {
        viewModelScope.launch {
            topLikedResult.postValue(songService.getTopLiked(token, limit))
        }
    }

    fun getSuggested(token: String, limit: Int) {
        viewModelScope.launch {
            suggestedResult.postValue(songService.getSuggested(token, limit))
        }
    }

    fun getRecentlyPlayed(token: String, limit: Int) {
        viewModelScope.launch {
            recentlyPlayedResult.postValue(songService.getRecentlyPlayed(token, limit))
        }
    }
}