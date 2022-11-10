package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody

class SongService {
    suspend fun uploadSong(
        token: String,
        uploadSongRequest: UploadSongRequest,
        song: MultipartBody.Part,
        image: MultipartBody.Part
    ) {
        return RetrofitInstance.songAPI.uploadSong(token, uploadSongRequest, song, image)
    }

    suspend fun getSuggested(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.songAPI.suggestedSongs(token, limit)
    }

    suspend fun getTopLiked(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.songAPI.getTopLikedSongs(token, limit)
    }

    suspend fun getRecentlyPlayed(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.songAPI.getRecentlyPlayedSongs(token, limit)
    }

    suspend fun getTopPlayed(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.songAPI.getTopPlayedSongs(token, limit)
    }

    suspend fun getSearchResult(token: String, input: String): SearchResponse {
        return RetrofitInstance.songAPI.searchForSong(token, input)
    }

    suspend fun getLikedSongs(token: String): List<SongResponse> {
        return RetrofitInstance.songAPI.getLikedSongs(token)
    }

    suspend fun searchLikedSongs(token: String, input: String): List<SongResponse> {
        return RetrofitInstance.songAPI.searchLikedSongs(token, input)
    }

    suspend fun getUnreleasedSong(token: String, limit: Int): List<SongResponse> {
        return RetrofitInstance.songAPI.getUnreleasedSongs(token, limit)
    }

    suspend fun publishSong(token: String, song_id: Int) {
        return RetrofitInstance.songAPI.publishSong(token, song_id)
    }

    suspend fun deleteFromAlbum(token: String, song_id: Int) {
        return RetrofitInstance.songAPI.deleteFromAlbum(token, song_id)
    }

    suspend fun isLiked(token: String, song_id: Int): Boolean {
        return RetrofitInstance.songAPI.isLiked(token, song_id)
    }
}