package com.example.recordily_client.responses

data class UserResponse(
    val biography: String,
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val profile_picture: String,
    val updated_at: String,
    val user_type_id: Int
)