package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.AlbumService
import com.example.recordily_client.services.SongService
import com.example.recordily_client.services.UserService
import kotlinx.coroutines.launch

class UnreleasedViewModel: ViewModel() {
    private val songService = SongService()
    private val albumService = AlbumService()

    private val unreleasedSongsResult = MutableLiveData<List<SongResponse>>()
    val unreleasedSongsResultLiveData : LiveData<List<SongResponse>>
        get() = unreleasedSongsResult

    private val unreleasedAlbumsResult = MutableLiveData<List<AlbumResponse>>()
    val unreleasedAlbumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = unreleasedAlbumsResult

    fun getUnreleasedSongs(token: String, limit: Int){
        viewModelScope.launch {
            unreleasedSongsResult.postValue(songService.getUnreleasedSong(token, limit))
        }
    }

    fun getUnreleasedAlbums(token: String, limit: Int){
        viewModelScope.launch {
            unreleasedAlbumsResult.postValue(albumService.getUnreleasedAlbum(token, limit))
        }
    }

    suspend fun publishSong(token: String, song_id: Int): Boolean {
        return try {
            songService.publishSong(token, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun publishAlbum(token: String, album_id: Int): Boolean {
        return try {
            albumService.publishAlbum(token, album_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}