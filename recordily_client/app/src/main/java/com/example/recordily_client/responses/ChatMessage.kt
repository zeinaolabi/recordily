package com.example.recordily_client.responses

data class ChatMessage(
    val id: String,
    val message: String,
    val fromID: Int,
    val roomID: String,
    val createdAt: Long
) {
    constructor(): this( "",  "", -1, "", 0)
}
