package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody

class SongService {
    suspend fun uploadSong(uploadSongRequest: UploadSongRequest, file: MultipartBody.Part) {
        return RetrofitInstance.songAPI.uploadSong(uploadSongRequest, file)
    }

    suspend fun getSuggested(limit: Int): List<SongResponse>{
        return RetrofitInstance.songAPI.suggestedSongs(limit)
    }

    suspend fun getTopLiked(limit: Int): List<SongResponse>{
        return RetrofitInstance.songAPI.getTopLikedSongs(limit)
    }

    suspend fun getRecentlyPlayed(limit: Int): List<SongResponse>{
        return RetrofitInstance.songAPI.getRecentlyPlayedSongs(limit)
    }

    suspend fun getTopPlayed(limit: Int): List<SongResponse>{
        return RetrofitInstance.songAPI.getTopPlayedSongs(limit)
    }

    suspend fun getSearchResult(input: String): SearchResponse{
        return RetrofitInstance.songAPI.searchForSong(input)
    }

    suspend fun getLikedSongs(token: String): List<SongResponse>{
        return RetrofitInstance.songAPI.getLikedSongs(token)
    }
}