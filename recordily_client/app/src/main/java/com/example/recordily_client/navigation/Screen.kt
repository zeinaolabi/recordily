package com.example.recordily_client.navigation

sealed class Screen(val route: String){
    object LoginPage: Screen(route = "login_screen")
    object RegistrationPage: Screen(route = "registration_screen")
    object CommonLandingPage: Screen(route = "common_landing_page")
    object CommonProfilePage: Screen(route = "common_profile_page")
    object UnreleasedPage: Screen(route = "unreleased_page")
    object RecordPage: Screen(route = "record_page")
    object ViewsStatsPage: Screen(route = "views_stats_page")
    object SongsStatsPage: Screen(route = "songs_stats_page")
    object CommonSearchPage: Screen(route = "common_search_page")
    object CommonLiveEventsPage: Screen(route = "common_live_page")
    object LibraryPage: Screen(route = "library_page")
    object PlaylistsPage: Screen(route = "playlists_page")
    object ArtistsPage: Screen(route = "artists_page")
    object PlaylistPage: Screen(route = "playlist_page")
    object ArtistProfilePage: Screen(route = "artist_page")
    object UploadSongPage: Screen(route = "upload_song_page")
    object UploadAlbumPage: Screen(route = "upload_album_page")
    object EditProfilePage: Screen(route = "edit_profile_page")
    object SongPage: Screen(route = "song_page")
    object SongStatsPage: Screen(route = "song_stats_page")

}
