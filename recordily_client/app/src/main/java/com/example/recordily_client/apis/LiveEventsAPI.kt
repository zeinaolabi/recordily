package com.example.recordily_client.apis

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LiveEventsAPI {
    @POST("auth/add_live_event")
    suspend fun addLiveEvent(@Header("Authorization") token: String, @Body name: String)
}