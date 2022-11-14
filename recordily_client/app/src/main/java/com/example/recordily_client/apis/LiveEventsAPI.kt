package com.example.recordily_client.apis

import com.example.recordily_client.requests.LiveEventRequest
import com.example.recordily_client.requests.MessageRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LiveEventsAPI {
    @POST("auth/add_live_event")
    suspend fun addLiveEvent(@Header("Authorization") token: String, @Body liveEventRequest: LiveEventRequest)

    @POST("auth/add_message")
    suspend fun addMessage(@Header("Authorization") token: String, @Body messageRequest: MessageRequest)
}