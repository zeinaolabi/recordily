package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
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

    fun isLiked(token: String, songID: Int){
        viewModelScope.launch {
            isLikedResult.postValue(songService.isLiked(token, songID))
        }
    }

    suspend fun likeSong(token: String, songID: Int): Boolean {
        return try {
            songService.likeSong(token, songID)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun unlikeSong(token: String, songID: Int): Boolean {
        return try {
            songService.unlikeSong(token, songID)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun addToPlaylist(token: String, playlistID: Int, songID: Int): Boolean {
        return try {
            playlistService.addToPlaylist(token, playlistID, songID)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun removeFromPlaylist(token: String, playlistID: Int, songID: Int): Boolean {
        return try {
            playlistService.removeFromPlaylist(token, playlistID, songID)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    fun playSong(songURL: String, liveEventID: String): Boolean{
        val reference = database.getReference("rooms/$liveEventID/song")

        return try {
            reference.setValue(songURL)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}