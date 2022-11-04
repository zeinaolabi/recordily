package com.example.recordily_client.responses

data class Song(
    val album_id: Int,
    val artist_name: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val path: String,
    val picture: String,
    val size: Int,
    val time_length: Int,
    val type: String,
    val updated_at: String,
    val user_id: Int
)