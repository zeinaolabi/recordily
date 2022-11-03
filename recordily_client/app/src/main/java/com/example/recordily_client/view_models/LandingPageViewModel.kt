package com.example.recordily_client.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class LandingPageViewModel(application: Application): AndroidViewModel(application){

    private val songService = SongService()

    private val topPlayedResult = MutableLiveData<List<SongResponse>>()
    val topPlayedResultLiveData : LiveData<List<SongResponse>>
        get() = topPlayedResult

    private val topLikedResult = MutableLiveData<List<SongResponse>>()
    val topLikedResultLiveData : LiveData<List<SongResponse>>
        get() = topLikedResult

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
}