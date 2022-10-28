package com.example.recordily_client.responses

data class LoginResponse(
    val id: Int,
    val user_type_id: Int,
    val token: String
)
