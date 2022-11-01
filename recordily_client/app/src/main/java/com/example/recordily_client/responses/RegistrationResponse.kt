package com.example.recordily_client.responses

data class RegistrationResponse(
    val created_at: String,
    val email: String,
    val id: Int,
    val token: String,
    val updated_at: String,
    val user_type_id: Int
)