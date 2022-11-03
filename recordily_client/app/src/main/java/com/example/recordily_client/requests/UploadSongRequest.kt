package com.example.recordily_client.requests

data class UploadSongRequest(
    val user_id: Int,
    val name: String,
    val image: String,
    val file: ByteArray,
    val chunks_size: Int,
    val chunk_num: Int
)
