package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class SuggestedSongsViewModel: ViewModel() {
    private val service = SongService()

    private val suggestedSongsResult = MutableLiveData<List<SongResponse>>()
    val suggestedSongsResultLiveData : LiveData<List<SongResponse>>
        get() = suggestedSongsResult

    fun getSuggestedSongs(token: String, limit: Int){
        viewModelScope.launch {
            suggestedSongsResult.postValue(service.getSuggested(token, limit))
        }
    }
}