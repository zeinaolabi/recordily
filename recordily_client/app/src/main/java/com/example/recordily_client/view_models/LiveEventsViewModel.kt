package com.example.recordily_client.view_models

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.recordily_client.requests.ChatMessage
import com.example.recordily_client.requests.LiveEvent
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.services.ArtistService
import com.example.recordily_client.services.LiveEventService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class LiveEventsViewModel(application: Application): AndroidViewModel(application) {
    private val database = FirebaseDatabase.getInstance("https://recordily-default-rtdb.firebaseio.com/")
    private val reference = database.getReference("rooms/")
    @SuppressLint("StaticFieldLeak")
    val context: Context = getApplication<Application>().applicationContext

    private val artistService = ArtistService()
    private val liveEventService = LiveEventService()

    private val liveEventsResult = MutableLiveData<List<LiveEvent>>()
    val liveEventsResultLiveData: LiveData<List<LiveEvent>>
        get() = liveEventsResult

    private val artistInfoResult = MutableLiveData<UserResponse>()
    val artistInfoResultLiveData: LiveData<UserResponse>
        get() = artistInfoResult

    fun getArtist(token: String, artist_id: String){
        viewModelScope.launch {
            artistInfoResult.postValue(artistService.getArtist(token, artist_id))
        }
    }

    fun getLiveEvents(){
        val liveEvents: ArrayList<LiveEvent> = ArrayList()

        reference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val liveEvent = snapshot.getValue(LiveEvent::class.java)

                if (liveEvent != null) {
                    liveEvents.add(
                        LiveEvent(
                            liveEvent.id,
                            liveEvent.name,
                            liveEvent.hostID,
                            liveEvent.createdAt,
                            hashMapOf()
                        )
                    )

                    liveEventsResult.postValue(liveEvents)
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

    suspend fun addLiveEvent(token: String, liveEventName: String, id: Int): String? {

        return try {
            liveEventService.addLiveEvent(token, liveEventName)

            val room = database.getReference("rooms/").push()

            val sentMessage = room.key?.let {
                LiveEvent(
                    it,
                    liveEventName,
                    id,
                    System.currentTimeMillis(),
                    hashMapOf())
            }

            room.setValue(sentMessage)

            room.key
        } catch (exception: Throwable) {
            null
        }
    }
}