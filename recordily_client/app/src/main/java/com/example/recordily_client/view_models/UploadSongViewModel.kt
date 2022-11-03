package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.repositories.UploadSongRepository
import com.example.recordily_client.requests.UploadSongRequest
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@SuppressLint("StaticFieldLeak")
class UploadSongViewModel(application: Application): AndroidViewModel(application) {

    val context: Context = getApplication<Application>().applicationContext
    private val repository = UploadSongRepository()

    fun uploadSong(uploadSongRequest: UploadSongRequest){
        viewModelScope.launch {
            repository.uploadSong(uploadSongRequest)
        }
    }
}