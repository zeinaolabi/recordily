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

    fun getSearchResult(token: String, input: String) {
        viewModelScope.launch {
            searchResult.postValue(songService.getSearchResult(token, input))
        }
    }

    suspend fun getSuggestedResult(token: String, limit: Int): List<SongResponse>{
        return songService.getSuggested(token, limit)
    }
}