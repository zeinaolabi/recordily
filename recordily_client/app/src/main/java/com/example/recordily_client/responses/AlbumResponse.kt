package com.example.recordily_client.responses

data class AlbumResponse(
    val created_at: String,
    val id: Int,
    val name: String,
    val picture: String,
    val updated_at: String,
    val user_id: Int,
    val artist_name: String
)