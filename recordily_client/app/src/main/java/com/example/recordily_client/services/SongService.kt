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

    suspend fun publishSong(token: String, songID: Int) {
        return RetrofitInstance.songAPI.publishSong(token, songID)
    }

    suspend fun deleteFromAlbum(token: String, songID: Int) {
        return RetrofitInstance.songAPI.deleteFromAlbum(token, songID)
    }

    suspend fun isLiked(token: String, songID: Int): Boolean {
        return RetrofitInstance.songAPI.isLiked(token, songID)
    }

    suspend fun likeSong(token: String, songID: Int) {
        return RetrofitInstance.songAPI.likeSong(token, songID)
    }

    suspend fun unlikeSong(token: String, songID: Int) {
        return RetrofitInstance.songAPI.unlikeSong(token, songID)
    }

    suspend fun getSongViewsPerMonth(token: String, songID: String): Array<Int> {
        return RetrofitInstance.songAPI.getSongViewsPerMonth(token, songID)
    }

    suspend fun getSongLikes(token: String, songID: String): Int {
        return RetrofitInstance.songAPI.getSongLikes(token, songID)
    }

    suspend fun getSongViews(token: String, songID: String): Int {
        return RetrofitInstance.songAPI.getSongViews(token, songID)
    }

    suspend fun getSong(token: String, songID: String): SongResponse {
        return RetrofitInstance.songAPI.getSong(token, songID)
    }

    suspend fun playSong(token: String, songID: Int) {
        return RetrofitInstance.songAPI.playSong(token, songID)
    }

    suspend fun search(token: String, input: String): List<SongResponse> {
        return RetrofitInstance.songAPI.search(token, input)
    }
}