package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.text.format.DateFormat
import androidx.lifecycle.*
import com.example.recordily_client.responses.ChatMessage
import com.example.recordily_client.requests.MessageRequest
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.ArtistService
import com.example.recordily_client.services.LiveEventService
import com.example.recordily_client.services.SongService
import com.google.firebase.database.*
import kotlinx.coroutines.launch
import java.io.IOException

val chatMessages: LinkedHashMap<String, ChatMessage> =
    mutableMapOf<String, ChatMessage>() as LinkedHashMap<String, ChatMessage>

@SuppressLint("StaticFieldLeak")
class LiveEventViewModel(application: Application): AndroidViewModel(application) {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")
    val context: Context = getApplication<Application>().applicationContext
    private val mediaPlayer = MediaPlayer()

    private val artistService = ArtistService()
    private val liveEventService = LiveEventService()
    private val songService = SongService()

    private val messagesResult = MutableLiveData<ChatMessage>()
    val messagesResultLiveData: LiveData<ChatMessage>
        get() = messagesResult

    private val hostPictureResult = MutableLiveData<UserResponse>()
    val hostPictureResultLiveData: LiveData<UserResponse>
        get() = hostPictureResult

    private val songResult = MutableLiveData<String>()
    val songResultLiveData: LiveData<String>
        get() = songResult

    private val songInfoResult = MutableLiveData<SongResponse>()
    val songInfoResultLiveData: LiveData<SongResponse>
        get() = songInfoResult

    private val isLiveResult = MutableLiveData<Boolean>()
    val isLiveResultLiveData: LiveData<Boolean>
        get() = isLiveResult

    suspend fun getArtist(token: String, artistID: String): UserResponse{
        return artistService.getArtist(token, artistID)
    }

    fun getSongInfo(token: String, songID: String){
        viewModelScope.launch {
            songInfoResult.postValue(songService.getSong(token, songID))
        }
    }

    fun getHostImage(token: String, artistID: String){
        viewModelScope.launch {
            hostPictureResult.postValue(artistService.getArtist(token, artistID))
        }
    }

    fun getMessages(liveEventID: String){
        val reference = database.getReference("rooms/$liveEventID/messages/")

        reference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                chatMessage?.let{
                    chatMessages[it.id] = it
                    messagesResult.postValue(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        })
    }

    suspend fun sendMessage(token: String, messageRequest: MessageRequest, id: Int): Boolean{
        val reference = database.getReference("rooms/${messageRequest.live_event_id}/messages").push()

        return try {
            liveEventService.addMessage(token, messageRequest)

            val sentMessage = reference.key?.let {
                ChatMessage(
                    it,
                    messageRequest.message,
                    id,
                    messageRequest.live_event_id,
                    System.currentTimeMillis()
                )
            }

            reference.setValue(sentMessage)
            true
        } catch (exception: Throwable) {
            false
        }
    }

    fun getSong(liveEventID: String) {
        val reference = database.getReference("rooms/$liveEventID/song")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                songResult.postValue(dataSnapshot.value as String?)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun isLive(liveEventID: String) {
        val reference = database.getReference("rooms/$liveEventID")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()) {
                    isLiveResult.postValue(true)
                } else {
                    isLiveResult.postValue(false)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun endLive(liveEventID: String) {
       database.getReference("rooms/$liveEventID").removeValue()
    }

    fun startPlayingSong(audioUrl: String) {
        if(!mediaPlayer.isPlaying) {
            try {
                mediaPlayer.reset()
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.isLooping = true
                mediaPlayer.prepareAsync()
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun stopPlayingSong() {
        try {
            mediaPlayer.stop()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    fun displayMessages(): LinkedHashMap<String, ChatMessage> {
        return chatMessages
    }

    fun clearMessages() {
        chatMessages.clear()
    }

    fun getDuration(audioUrl: String): Long? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(audioUrl)
        val durationStr: String? = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)

        return durationStr?.toLong()
    }

    fun convertDate(dateInMilliseconds: Long, dateFormat: String): String {
        return DateFormat.format(dateFormat, dateInMilliseconds).toString()
    }
}