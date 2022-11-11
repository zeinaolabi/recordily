package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.services.ArtistService
import com.example.recordily_client.services.SongService
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

@SuppressLint("StaticFieldLeak")
class UploadSongViewModel(application: Application): AndroidViewModel(application) {

    val context: Context = getApplication<Application>().applicationContext
    private val service = SongService()
    private val userService = UserService()

    private val albumsResult = MutableLiveData<List<AlbumResponse>>()
    val albumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = albumsResult

    fun getAlbums(token: String){
        viewModelScope.launch {
            albumsResult.postValue(userService.getAlbums(token))
        }
    }

    suspend fun uploadSong(
        token: String,
        uploadSongRequest: UploadSongRequest,
        song: MultipartBody.Part,
        image: MultipartBody.Part
    ): Boolean {
        return try {
            service.uploadSong(token, uploadSongRequest, song, image)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}