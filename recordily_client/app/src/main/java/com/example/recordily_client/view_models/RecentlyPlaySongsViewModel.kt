package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class RecentlyPlaySongsViewModel: ViewModel() {
    private val userService = UserService()

    private val recentlyPlayResult = MutableLiveData<List<SongResponse>>()
    val recentlyPlayResultLiveData : LiveData<List<SongResponse>>
        get() = recentlyPlayResult

    fun getRecentlyPlayed(token: String, limit: Int){
        viewModelScope.launch {
            recentlyPlayResult.postValue(userService.getRecentlyPlayed(token, limit))
        }
    }
}