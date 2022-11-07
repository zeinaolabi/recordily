package com.example.recordily_client.responses

data class SearchResponse(
    val artists: List<UserResponse>,
    val songs: List<SongResponse>
)