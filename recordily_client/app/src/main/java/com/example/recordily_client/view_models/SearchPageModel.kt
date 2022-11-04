package com.example.recordily_client.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class SearchPageViewModel(application: Application): AndroidViewModel(application){

    private val songService = SongService()

    private val searchResult = MutableLiveData<SearchResponse>()
    val searchResultLiveData : LiveData<SearchResponse>
        get() = searchResult

    private val suggestedResult = MutableLiveData<List<SongResponse>>()
    val suggestedResultLiveData : LiveData<List<SongResponse>>
        get() = suggestedResult

    fun getSearchResult(input: String) {
        viewModelScope.launch {
            searchResult.postValue(songService.getSearchResult(input))
        }
    }

    fun getSuggestedResult(limit: Int) {
        viewModelScope.launch {
            suggestedResult.postValue(songService.getSuggested(limit))
        }
    }

}