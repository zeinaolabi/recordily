package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.recordily_client.requests.MessageRequest
import com.example.recordily_client.responses.ChatMessage
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.PlaylistService
import com.example.recordily_client.services.SongService
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class PopUpViewModel(application: Application): AndroidViewModel(application) {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")
    val context: Context = getApplication<Application>().applicationContext
    private val songService = SongService()
    private val playlistService = PlaylistService()

    private val isLikedResult = MutableLiveData<Boolean>()
    val isLikedResultLiveData : LiveData<Boolean>
        get() = isLikedResult

    private val playlistsResult = MutableLiveData<List<PlaylistResponse>>()
    val playlistsResultLiveData : LiveData<List<PlaylistResponse>>
        get() = playlistsResult

    private val searchResult = MutableLiveData<List<SongResponse>>()
    val searchResultLiveData : LiveData<List<SongResponse>>
        get() = searchResult

    fun getSearchResult(token: String, input: String){
        viewModelScope.launch {
            searchResult.postValue(songService.search(token, input))
        }
    }

    fun getPlaylists(token: String){
        viewModelScope.launch {
            playlistsResult.postValue(playlistService.getPlaylists(token))
        }
    }

    fun isLiked(token: String, song_id: Int){
        viewModelScope.launch {
            isLikedResult.postValue(songService.isLiked(token, song_id))
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

    suspend fun playSong(songURL: String, live_event_id: String): Boolean{
        val reference = database.getReference("rooms/$live_event_id/song")

        return try {
            reference.setValue(songURL)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}