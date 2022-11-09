package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class UnreleasedViewModel: ViewModel() {
    private val songService = SongService()

    private val unreleasedSongsResult = MutableLiveData<List<SongResponse>>()
    val unreleasedSongsResultLiveData : LiveData<List<SongResponse>>
        get() = unreleasedSongsResult

    fun getUnreleasedSongs(token: String, limit: Int){
        viewModelScope.launch {
            unreleasedSongsResult.postValue(songService.getUnreleasedSong(token, limit))
        }
    }
}