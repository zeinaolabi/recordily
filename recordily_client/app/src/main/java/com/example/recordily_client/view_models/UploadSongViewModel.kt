package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

@SuppressLint("StaticFieldLeak")
class UploadSongViewModel(application: Application): AndroidViewModel(application) {

    val context: Context = getApplication<Application>().applicationContext
    private val service = SongService()

    fun uploadSong(uploadSongRequest: UploadSongRequest, file: MultipartBody.Part){
        viewModelScope.launch {
            service.uploadSong(uploadSongRequest, file)
        }
    }
}