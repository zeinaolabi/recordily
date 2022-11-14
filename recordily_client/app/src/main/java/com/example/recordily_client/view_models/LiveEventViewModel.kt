package com.example.recordily_client.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recordily_client.requests.ChatMessage
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class LiveEventViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")

    private val messagesResult = MutableLiveData<List<ChatMessage>>()
    val messagesResultLiveData: LiveData<List<ChatMessage>>
        get() = messagesResult

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
        val reference = database.getReference("rooms/$live_event_id/messages" ).push()

        val sentMessage = reference.key?.let {
            ChatMessage(it, message, id, live_event_id, System.currentTimeMillis())
        }
        reference.setValue(sentMessage)
    }
}