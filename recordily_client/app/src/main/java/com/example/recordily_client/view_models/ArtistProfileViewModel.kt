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
    private val artistService = ArtistService()

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

    fun getArtist(token: String, artistID: String){
        viewModelScope.launch {
            artistInfoResult.postValue(artistService.getArtist(token, artistID))
        }
    }

    fun getArtistFollowers(token: String, artistID: String){
        viewModelScope.launch {
            artistFollowersResult.postValue(artistService.getArtistFollowers(token, artistID))
        }
    }

    fun isFollowed(token: String, artistID: String){
        viewModelScope.launch {
            isFollowedResult.postValue(artistService.isFollowed(token, artistID))
        }
    }

    fun getAlbums(token: String, artistID: String, limit: Int){
        viewModelScope.launch {
            albumsResult.postValue(artistService.getAlbums(token, artistID, limit))
        }
    }

    fun getArtistTopSongs(token: String, artistID: String, limit: Int){
        viewModelScope.launch {
            artistTopSongsResult.postValue(artistService.getArtistTopSongs(token, artistID, limit))
        }
    }

    fun getArtistSongs(token: String, artistID: String, limit: Int){
        viewModelScope.launch {
            artistSongsResult.postValue(artistService.getArtistSongs(token, artistID, limit))
        }
    }

    fun getArtistTopAlbums(token: String, artistID: String, limit: Int){
        viewModelScope.launch {
            artistTopAlbumsResult.postValue(artistService.getArtistTopAlbums(token, artistID, limit))
        }
    }

    suspend fun follow(token: String, artistID: String): Boolean {
        return try {
            artistService.follow(token, artistID)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    suspend fun unfollow(token: String, artistID: String): Boolean {
        return try {
            artistService.unfollow(token, artistID)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}