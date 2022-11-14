package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance
import com.example.recordily_client.requests.LiveEventRequest
import com.example.recordily_client.requests.MessageRequest

class LiveEventService {
    suspend fun addLiveEvent(token: String, liveEventRequest: LiveEventRequest) {
        return RetrofitInstance.liveEventsAPI.addLiveEvent(token, liveEventRequest)
    }

    suspend fun addMessage(token: String, messageRequest: MessageRequest) {
        return RetrofitInstance.liveEventsAPI.addMessage(token, messageRequest )
    }
}