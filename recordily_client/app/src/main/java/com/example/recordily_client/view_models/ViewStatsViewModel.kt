package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ViewStatsViewModel: ViewModel() {
    private val artistService = ArtistService()

    private val viewsResult = MutableLiveData<Array<Int>>()
    val viewsResultLiveData: LiveData<Array<Int>>
        get() = viewsResult

    fun getViewsPerMonth(token: String) {
        viewModelScope.launch {
            viewsResult.postValue(artistService.getViewsPerMonth(token))
        }
    }
}