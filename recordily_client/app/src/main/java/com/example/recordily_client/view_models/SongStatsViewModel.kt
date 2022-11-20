package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class SongStatsViewModel: ViewModel() {
    val songService = SongService()

    private val viewsPerMonthResult = MutableLiveData<Array<Int>>()
    val viewsPerMonthResultLiveData: LiveData<Array<Int>>
        get() = viewsPerMonthResult

    private val viewsResult = MutableLiveData<Int>()
    val viewsResultLiveData: LiveData<Int>
        get() = viewsResult

    private val likesResult = MutableLiveData<Int>()
    val likesResultLiveData: LiveData<Int>
        get() = likesResult

    private val songResult = MutableLiveData<SongResponse>()
    val songResultLiveData: LiveData<SongResponse>
        get() = songResult

    fun getSongViewsPerMonth(token: String, songID: String){
        viewModelScope.launch{
            viewsPerMonthResult.postValue(songService.getSongViewsPerMonth(token, songID))
        }
    }

    fun getSongViews(token: String, songID: String){
        viewModelScope.launch{
            viewsResult.postValue(songService.getSongViews(token, songID))
        }
    }

    fun getSongLikes(token: String, songID: String){
        viewModelScope.launch{
            likesResult.postValue(songService.getSongLikes(token, songID))
        }
    }

    fun getSong(token: String, songID: String){
        viewModelScope.launch{
            songResult.postValue(songService.getSong(token, songID))
        }
    }
}