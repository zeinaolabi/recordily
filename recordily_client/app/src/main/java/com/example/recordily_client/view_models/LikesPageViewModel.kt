package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class LikesPageViewModel: ViewModel(){
    private val songService = SongService()

    private val likedSongsResult = MutableLiveData<List<SongResponse>>()
    val likedSongsResultLiveData : LiveData<List<SongResponse>>
        get() = likedSongsResult

    private val searchResult = MutableLiveData<List<SongResponse>>()
    val searchResultLiveData : LiveData<List<SongResponse>>
        get() = searchResult

    fun getLikedSongs(token:String){
        viewModelScope.launch {
            likedSongsResult.postValue(songService.getLikedSongs(token))
        }
    }

    fun searchLikedSongs(token:String, input: String){
        viewModelScope.launch {
            searchResult.postValue(songService.searchLikedSongs(token, input))
        }
    }
}