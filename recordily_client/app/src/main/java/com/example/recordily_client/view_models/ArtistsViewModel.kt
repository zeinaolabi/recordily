package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.ArtistResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ArtistsViewModel: ViewModel() {
    private val service = ArtistService()

    private val followedArtistsResult = MutableLiveData<List<ArtistResponse>>()
    val followedArtistsResultLiveData : LiveData<List<ArtistResponse>>
        get() = followedArtistsResult

    private val searchArtistsResult = MutableLiveData<List<ArtistResponse>>()
    val searchArtistsResultLiveData : LiveData<List<ArtistResponse>>
        get() = searchArtistsResult

    fun getFollowedArtists(token: String){
        viewModelScope.launch {
            followedArtistsResult.postValue(service.followedArtists(token))
        }
    }

    fun searchFollowedArtists(token: String, input: String){
        viewModelScope.launch {
            searchArtistsResult.postValue(service.searchFollowedArtist(token, input))
        }
    }
}