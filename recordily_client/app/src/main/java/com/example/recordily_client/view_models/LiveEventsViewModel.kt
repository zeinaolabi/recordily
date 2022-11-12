package com.example.recordily_client.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recordily_client.requests.ChatMessage
import com.example.recordily_client.requests.LiveEvent
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class LiveEventsViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")
    private val reference = database.getReference("rooms/")

    private val liveEventsResult = MutableLiveData<List<LiveEvent>>()
    val liveEventsResultLiveData: LiveData<List<LiveEvent>>
        get() = liveEventsResult

    fun getLiveEvents(){
        val liveEvents: ArrayList<LiveEvent> = ArrayList()

        reference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val liveEvent = snapshot.getValue(LiveEvent::class.java)

                if (liveEvent != null) {
                    liveEvents.add(LiveEvent(
                        liveEvent.id,
                        liveEvent.name,
                        liveEvent.hostID,
                        liveEvent.createdAt,
                        ChatMessage())
                    )
                }

                liveEventsResult.postValue(liveEvents)
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

    fun addLiveEvent(liveEventName: String, id: Int): String? {
        reference.push()

        val sentMessage = reference.key?.let {
            LiveEvent(
                it,
                liveEventName,
                id,
                System.currentTimeMillis(),
                null)
        }

        reference.setValue(sentMessage)

        return reference.key
    }
}