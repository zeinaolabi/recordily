package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class TopSongsViewModel: ViewModel() {
    private val service = UserService()

    private val topSongsResult = MutableLiveData<List<SongResponse>>()
    val topSongsResultLiveData : LiveData<List<SongResponse>>
        get() = topSongsResult

    fun getTopSongs(token: String, limit: Int){
        viewModelScope.launch {
            topSongsResult.postValue(service.getTopSongs(token, limit))
        }
    }
}