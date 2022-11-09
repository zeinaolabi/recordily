package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class UnreleasedSongsViewModel: ViewModel() {
    private val service = SongService()

    private val unreleasedSongsResult = MutableLiveData<List<SongResponse>>()
    val unreleasedSongsResultLiveData : LiveData<List<SongResponse>>
        get() = unreleasedSongsResult

    fun getUnreleasedSongs(token: String, limit: Int){
        viewModelScope.launch {
            unreleasedSongsResult.postValue(service.getUnreleasedSong(token, limit))
        }
    }

    suspend fun publishSong(token: String, song_id: Int): Boolean {
        return try {
            service.publishSong(token, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}