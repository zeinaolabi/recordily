package com.example.recordily_client.responses

data class PlaylistResponse(
    val created_at: String,
    val id: Int,
    val name: String,
    val picture: String?,
    val updated_at: String,
    val user_id: Int
)