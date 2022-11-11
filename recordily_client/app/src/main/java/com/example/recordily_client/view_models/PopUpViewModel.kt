package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.services.PlaylistService
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class PopUpViewModel(application: Application): AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext
    private val songService = SongService()
    private val playlistService = PlaylistService()

    private val isLikedResult = MutableLiveData<Boolean>()
    val isLikedResultLiveData : LiveData<Boolean>
        get() = isLikedResult

    private val playlistsResult = MutableLiveData<List<PlaylistResponse>>()
    val playlistsResultLiveData : LiveData<List<PlaylistResponse>>
        get() = playlistsResult

    fun isLiked(token: String, song_id: Int){
        viewModelScope.launch {
            isLikedResult.postValue(songService.isLiked(token, song_id))
        }
    }

    fun getPlaylists(token: String){
        viewModelScope.launch {
            playlistsResult.postValue(playlistService.getPlaylists(token))
        }
    }

    suspend fun likeSong(token: String, song_id: Int): Boolean {
        return try {
            songService.likeSong(token, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun unlikeSong(token: String, song_id: Int): Boolean {
        return try {
            songService.unlikeSong(token, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun addToPlaylist(token: String, playlist_id: Int, song_id: Int): Boolean {
        return try {
            playlistService.addToPlaylist(token, playlist_id, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun removeFromPlaylist(token: String, playlist_id: Int, song_id: Int): Boolean {
        return try {
            playlistService.removeFromPlaylist(token, playlist_id, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}