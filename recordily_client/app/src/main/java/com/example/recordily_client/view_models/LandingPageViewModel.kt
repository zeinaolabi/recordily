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

    fun getTopPlayed(limit: Int) {
        viewModelScope.launch {
            topPlayedResult.postValue(songService.getTopPlayed(limit))
        }
    }

    fun getTopLiked(limit: Int) {
        viewModelScope.launch {
            topPlayedResult.postValue(songService.getTopLiked(limit))
        }
    }

    fun getSuggested(limit: Int) {
        viewModelScope.launch {
            topPlayedResult.postValue(songService.getSuggested(limit))
        }
    }

    fun getRecentlyPlayed(limit: Int) {
        viewModelScope.launch {
            topPlayedResult.postValue(songService.getRecentlyPlayed(limit))
        }
    }
}