package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.recordily_client.responses.ChatMessage
import com.example.recordily_client.requests.MessageRequest
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.ArtistService
import com.example.recordily_client.services.LiveEventService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class LiveEventViewModel(application: Application): AndroidViewModel(application) {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")
    val context: Context = getApplication<Application>().applicationContext

    private val artistService = ArtistService()
    private val liveEventService = LiveEventService()

    private val messagesResult = MutableLiveData<ChatMessage>()
    val messagesResultLiveData: LiveData<ChatMessage>
        get() = messagesResult

    private val userInfoResult = MutableLiveData<UserResponse>()
    val userInfoResultLiveData: LiveData<UserResponse>
        get() = userInfoResult

    private val hostPictureResult = MutableLiveData<UserResponse>()
    val hostPictureResultLiveData: LiveData<UserResponse>
        get() = hostPictureResult

    fun getArtist(token: String, artist_id: String){
        viewModelScope.launch {
            userInfoResult.postValue(artistService.getArtist(token, artist_id))
        }
    }

    fun getHostImage(token: String, artist_id: String){
        viewModelScope.launch {
            hostPictureResult.postValue(artistService.getArtist(token, artist_id))
        }
    }

    fun getMessages(live_event_id: String){
        val reference = database.getReference("rooms/$live_event_id/messages/")

        reference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    messagesResult.postValue(chatMessage)
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
                ChatMessage(it, messageRequest.message, id, messageRequest.live_event_id, System.currentTimeMillis())
            }

            reference.setValue(sentMessage)
            true
        } catch (exception: Throwable) {
            false
        }
    }
}