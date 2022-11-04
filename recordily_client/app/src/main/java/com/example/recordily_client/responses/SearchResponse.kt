package com.example.recordily_client.responses

data class SearchResponse(
    val artists: List<ArtistResponse>,
    val songs: List<SongResponse>
)