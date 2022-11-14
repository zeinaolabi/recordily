package com.example.recordily_client.responses

data class LiveEvent(
    val id : String,
    val name: String,
    val hostID: Int,
    val createdAt: Long,
    val messages: HashMap<String, ChatMessage>
) {
    constructor(): this( "",  "", -1, -1, hashMapOf<String, ChatMessage>())
}
