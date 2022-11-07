package com.example.recordily_client.view_models

import androidx.lifecycle.*
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class SearchPageViewModel: ViewModel(){

    private val songService = SongService()

    private val searchResult = MutableLiveData<SearchResponse>()
    val searchResultLiveData : LiveData<SearchResponse>
        get() = searchResult

    private val suggestedResult = MutableLiveData<List<SongResponse>>()
    val suggestedResultLiveData : LiveData<List<SongResponse>>
        get() = suggestedResult

    fun getSearchResult(token: String, input: String) {
        viewModelScope.launch {
            searchResult.postValue(songService.getSearchResult(token, input))
        }
    }

    fun getSuggestedResult(token: String, limit: Int) {
        viewModelScope.launch {
            suggestedResult.postValue(songService.getSuggested(token, limit))
        }
    }

}