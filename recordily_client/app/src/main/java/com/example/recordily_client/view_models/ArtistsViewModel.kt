package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ArtistsViewModel: ViewModel() {
    private val artistService = ArtistService()

    private val followedArtistsResult = MutableLiveData<List<UserResponse>>()
    val followedArtistsResultLiveData : LiveData<List<UserResponse>>
        get() = followedArtistsResult

    private val searchArtistsResult = MutableLiveData<List<UserResponse>>()
    val searchArtistsResultLiveData : LiveData<List<UserResponse>>
        get() = searchArtistsResult

    fun getFollowedArtists(token: String){
        viewModelScope.launch {
            followedArtistsResult.postValue(artistService.followedArtists(token))
        }
    }

    fun searchFollowedArtists(token: String, input: String){
        viewModelScope.launch {
            searchArtistsResult.postValue(artistService.searchFollowedArtist(token, input))
        }
    }
}