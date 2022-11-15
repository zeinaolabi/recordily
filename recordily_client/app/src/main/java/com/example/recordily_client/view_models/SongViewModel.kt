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
import androidx.compose.runtime.mutableStateOf
import kotlin.time.Duration.Companion.milliseconds

val queue = mutableListOf<Int>()
val currentIndex = mutableStateOf(-1)

class SongViewModel: ViewModel() {
    private val songService = SongService()
    private val mediaPlayer = MediaPlayer()

    private val songResult = MutableLiveData<SongResponse>()
    val songResultLiveData : LiveData<SongResponse>
        get() = songResult

    fun getSong(token: String, song_id: String){
        viewModelScope.launch {
            songResult.postValue(songService.getSong(token, song_id))
        }
    }

    fun playSong(audioUrl: String) {
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