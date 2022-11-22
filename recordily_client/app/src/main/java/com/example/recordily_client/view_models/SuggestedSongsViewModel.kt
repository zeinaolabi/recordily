package com.example.recordily_client.view_models

import androidx.lifecycle.ViewModel
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService

class SuggestedSongsViewModel: ViewModel() {
    private val songService = SongService()

    suspend fun getSuggestedSongs(token: String, limit: Int): List<SongResponse>{
        return songService.getSuggested(token, limit)
    }
}