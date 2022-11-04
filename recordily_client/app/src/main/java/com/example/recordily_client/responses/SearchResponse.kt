package com.example.recordily_client.responses

data class SearchResponse(
    val artists: List<Any>,
    val songs: List<Song>
)