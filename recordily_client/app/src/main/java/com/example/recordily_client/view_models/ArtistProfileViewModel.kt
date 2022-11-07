package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.ArtistResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ArtistProfileViewModel: ViewModel() {
    private val service = ArtistService()

    private val artistInfoResult = MutableLiveData<ArtistResponse>()
    val artistInfoResultLiveData : LiveData<ArtistResponse>
        get() = artistInfoResult

    fun getArtist(token: String, artist_id: String){
        viewModelScope.launch {
            artistInfoResult.postValue(service.getArtist(token, artist_id))
        }
    }
}