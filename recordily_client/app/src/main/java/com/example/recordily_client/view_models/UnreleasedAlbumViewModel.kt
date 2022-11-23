package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.AlbumService
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch

class UnreleasedAlbumViewModel: ViewModel() {
    private val albumService = AlbumService()
    private val songService = SongService()

    private val albumInfoResult = MutableLiveData<AlbumResponse>()
    val albumInfoResultLiveData : LiveData<AlbumResponse>
        get() = albumInfoResult

    private val albumSongsResult = MutableLiveData<List<SongResponse>>()
    val albumSongsResultLiveData : LiveData<List<SongResponse>>
        get() = albumSongsResult

    fun getAlbumInfo(token: String, artistID: String){
        viewModelScope.launch {
            albumInfoResult.postValue(albumService.getAlbumInfo(token, artistID))
        }
    }

    fun getAlbumSongs(token: String, artistID: String){
        viewModelScope.launch {
            albumSongsResult.postValue(albumService.getAlbumSongs(token, artistID))
        }
    }

    suspend fun deleteFromAlbum(token: String, songID: Int): Boolean {
        return try {
            songService.deleteFromAlbum(token, songID)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}