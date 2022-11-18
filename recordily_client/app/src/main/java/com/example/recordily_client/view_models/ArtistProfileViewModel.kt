package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.ArtistService
import kotlinx.coroutines.launch

class ArtistProfileViewModel: ViewModel() {
    private val service = ArtistService()

    private val artistInfoResult = MutableLiveData<UserResponse>()
    val artistInfoResultLiveData : LiveData<UserResponse>
        get() = artistInfoResult

    private val artistFollowersResult = MutableLiveData<Int>()
    val artistFollowersResultLiveData : LiveData<Int>
        get() = artistFollowersResult

    private val isFollowedResult = MutableLiveData<Boolean>()
    val isFollowedResultLiveData : LiveData<Boolean>
        get() = isFollowedResult

    private val albumsResult = MutableLiveData<List<AlbumResponse>>()
    val albumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = albumsResult

    private val artistTopSongsResult = MutableLiveData<List<SongResponse>>()
    val artistTopSongsResultLiveData : LiveData<List<SongResponse>>
        get() = artistTopSongsResult

    private val artistSongsResult = MutableLiveData<List<SongResponse>>()
    val artistSongsResultLiveData : LiveData<List<SongResponse>>
        get() = artistSongsResult

    private val artistTopAlbumsResult = MutableLiveData<List<AlbumResponse>>()
    val artistTopAlbumsResultLiveData : LiveData<List<AlbumResponse>>
        get() = artistTopAlbumsResult

    fun getArtist(token: String, artist_id: String){
        viewModelScope.launch {
            artistInfoResult.postValue(service.getArtist(token, artist_id))
        }
    }

    fun getArtistFollowers(token: String, artist_id: String){
        viewModelScope.launch {
            artistFollowersResult.postValue(service.getArtistFollowers(token, artist_id))
        }
    }

    fun isFollowed(token: String, artist_id: String){
        viewModelScope.launch {
            isFollowedResult.postValue(service.isFollowed(token, artist_id))
        }
    }

    fun getAlbums(token: String, artist_id: String, limit: Int){
        viewModelScope.launch {
            albumsResult.postValue(service.getAlbums(token, artist_id, limit))
        }
    }

    fun getArtistTopSongs(token: String, artist_id: String, limit: Int){
        viewModelScope.launch {
            artistTopSongsResult.postValue(service.getArtistTopSongs(token, artist_id, limit))
        }
    }

    fun getArtistSongs(token: String, artist_id: String, limit: Int){
        viewModelScope.launch {
            artistSongsResult.postValue(service.getArtistSongs(token, artist_id, limit))
        }
    }

    fun getArtistTopAlbums(token: String, artist_id: String, limit: Int){
        viewModelScope.launch {
            artistTopAlbumsResult.postValue(service.getArtistTopAlbums(token, artist_id, limit))
        }
    }

    suspend fun follow(token: String, artist_id: String): Boolean {
        return try {
            service.follow(token, artist_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun unfollow(token: String, artist_id: String): Boolean {
        return try {
            service.unfollow(token, artist_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}