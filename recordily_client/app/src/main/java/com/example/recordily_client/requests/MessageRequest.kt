package com.example.recordily_client.requests

data class MessageRequest(
    val message: String,
    val live_event_id: String
)