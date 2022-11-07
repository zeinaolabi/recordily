package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.ArtistResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class ArtistProfileViewModel: ViewModel() {
    private val service = ArtistService()

    private val artistInfoResult = MutableLiveData<ArtistResponse>()
    val artistInfoResultLiveData : LiveData<ArtistResponse>
        get() = artistInfoResult

    private val artistFollowersResult = MutableLiveData<Int>()
    val artistFollowersResultLiveData : LiveData<Int>
        get() = artistFollowersResult

    private val isFollowedResult = MutableLiveData<Int>()
    val isFollowedResultLiveData : LiveData<Int>
        get() = isFollowedResult

    fun getArtist(token: String, artist_id: String){
        viewModelScope.launch {
            artistInfoResult.postValue(service.getArtist(token, artist_id))
        }
    }

    fun getArtistFollowers(token: String, artist_id: String){
        viewModelScope.launch {
            artistFollowersResult.postValue(service.getArtistFollowers(token, artist_id))
        }
    }

    fun isFollowed(token: String, artist_id: String){
        viewModelScope.launch {
            isFollowedResult.postValue(service.isFollowed(token, artist_id))
        }
    }
}