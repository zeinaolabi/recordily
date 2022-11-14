package com.example.recordily_client.services

import com.example.recordily_client.apis.RetrofitInstance

class LiveEventService {
    suspend fun addLiveEvent(token: String, name: String) {
        return RetrofitInstance.liveEventsAPI.addLiveEvent(token, name)
    }
}