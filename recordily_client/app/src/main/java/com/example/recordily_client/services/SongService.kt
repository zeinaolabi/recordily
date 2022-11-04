package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse

class SongService {
    suspend fun uploadSong(uploadSongRequest: UploadSongRequest): Boolean {
        return RetrofitInstance.songApi.uploadSong(uploadSongRequest)
    }

    suspend fun getSuggested(limit: Int): List<SongResponse>{
        return RetrofitInstance.songApi.suggestedSongs(limit)
    }

    suspend fun getTopLiked(limit: Int): List<SongResponse>{
        return RetrofitInstance.songApi.getTopLikedSongs(limit)
    }

    suspend fun getRecentlyPlayed(limit: Int): List<SongResponse>{
        return RetrofitInstance.songApi.getRecentlyPlayedSongs(limit)
    }

    suspend fun getTopPlayed(limit: Int): List<SongResponse>{
        return RetrofitInstance.songApi.getTopPlayedSongs(limit)
    }

    suspend fun getSearchResult(input: String): SearchResponse{
        return RetrofitInstance.songApi.searchForSong(input)
    }

    suspend fun getLikedSongs(token: String): List<SongResponse>{
        return RetrofitInstance.songApi.getLikedSongs(token)
    }
}