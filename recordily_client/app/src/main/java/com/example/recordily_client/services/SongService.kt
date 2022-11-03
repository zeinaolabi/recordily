package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.SongResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SongService {
    suspend fun uploadSong(uploadSongRequest: UploadSongRequest): Boolean {
        return RetrofitInstance.uploadSong.uploadSong(uploadSongRequest)
    }

    suspend fun getTopPlayed(): List<SongResponse>
    {
        return RetrofitInstance.songApi.topSongs()
    }
}