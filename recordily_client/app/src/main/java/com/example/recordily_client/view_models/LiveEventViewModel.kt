package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recordily_client.requests.ChatMessage
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.ArtistService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class LiveEventViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")

    private val artistService = ArtistService()

    private val messagesResult = MutableLiveData<List<ChatMessage>>()
    val messagesResultLiveData: LiveData<List<ChatMessage>>
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
        val messages: ArrayList<ChatMessage> = ArrayList()
        val reference = database.getReference("rooms/$live_event_id/messages/")

        reference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    messages.add(
                        ChatMessage(
                            chatMessage.id,
                            chatMessage.message,
                            chatMessage.fromID,
                            chatMessage.roomID,
                            chatMessage.createdAt
                        )
                    )

                    messagesResult.postValue(messages)
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

    fun sendMessage(message: String, live_event_id: String, id: Int){
        val reference = database.getReference("rooms/$live_event_id/messages").push()

        val sentMessage = reference.key?.let {
            ChatMessage(it, message, id, live_event_id, System.currentTimeMillis())
        }

        reference.setValue(sentMessage)
    }
}