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

    private val songsResult = MutableLiveData<List<SongResponse>>()

    val songResultLiveData : LiveData<List<SongResponse>>
        get() = songsResult

    private val songService = SongService()

    fun getSongsList()
    {
        viewModelScope.launch {
            songsResult.postValue(songService.getTopPlayed())
        }

    }
}