package com.example.recordily_client.view_models

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.services.SongService
import kotlinx.coroutines.launch
import java.io.IOException
import android.media.MediaMetadataRetriever
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlin.time.Duration.Companion.milliseconds

class SongViewModel: ViewModel() {
    private val songService = SongService()
    private val mediaPlayer = MediaPlayer()

    val queue: MutableList<Int> = mutableStateListOf()

    private val songResult = MutableLiveData<SongResponse>()
    val songResultLiveData : LiveData<SongResponse>
        get() = songResult

    private val isLikedResult = MutableLiveData<Boolean>()
    val isLikedResultLiveData : LiveData<Boolean>
        get() = isLikedResult

    fun getSong(token: String, song_id: String){
        viewModelScope.launch {
            songResult.postValue(songService.getSong(token, song_id))
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

    suspend fun playSong(token: String, song_id: Int): Boolean {
        return try {
            songService.playSong(token, song_id)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    fun startPlayingSong(audioUrl: String) {
        try {
            mediaPlayer.setDataSource(audioUrl)
            mediaPlayer.isLooping = true
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun pauseSong() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        } catch (we: Exception) {
            we.printStackTrace()
        }
    }

    fun resumeSong() {
        try {
            mediaPlayer.start()
        } catch (we: Exception) {
            we.printStackTrace()
        }
    }

    fun getDurationAsString(audioUrl: String): String? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(audioUrl)
        val durationStr: String? = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val millSecond = durationStr?.toInt()

        val durationString = millSecond?.milliseconds?.toComponents { minutes, seconds, _ ->
            "%02d:%02d".format(minutes, seconds)
        }

        return durationString
    }

    fun getDuration(audioUrl: String): Long? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(audioUrl)
        val durationStr: String? = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        return durationStr?.toLong()
    }
}