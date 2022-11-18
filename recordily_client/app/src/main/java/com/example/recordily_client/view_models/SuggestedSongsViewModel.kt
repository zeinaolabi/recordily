package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class SuggestedSongsViewModel: ViewModel() {
    private val service = SongService()

    suspend fun getSuggestedSongs(token: String, limit: Int): List<SongResponse>{
        return service.getSuggested(token, limit)
    }
}