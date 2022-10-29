package com.example.recordily_client.requests

data class RegistrationRequest(
    val email: String,
    val password: String,
    val user_type_id: Int
)
