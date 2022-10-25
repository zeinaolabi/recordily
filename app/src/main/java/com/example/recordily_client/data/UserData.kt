package com.example.recordily_client.data

data class UserData(
    val id: Int,
    val email: String,
    val password: String,
    val access_token: String,
    val user_type_id: Int
)
