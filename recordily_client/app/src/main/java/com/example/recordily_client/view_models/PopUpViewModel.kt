package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class PopUpViewModel: ViewModel() {
    val service = SongService()

    private val isLikedResult = MutableLiveData<Boolean>()
    val isLikedResultLiveData : LiveData<Boolean>
        get() = isLikedResult

    fun isLiked(token: String, song_id: Int){
        viewModelScope.launch {
            isLikedResult.postValue(service.isLiked(token, song_id))
        }
    }
}