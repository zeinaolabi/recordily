package com.example.recordily_client.navigation

import androidx.annotation.StringRes
import com.example.recordily_client.R

sealed class TopNavItem(
    @StringRes val page: Int,
    val route: String
) {
    object ProfilePage : TopNavItem(
        page = R.string.profile,
        route = Screen.ProfilePage.route
    )

    object UnreleasedPage : TopNavItem(
        page = R.string.unreleased,
        route = Screen.UnreleasedPage.route
    )

    object HomePage : TopNavItem(
        page = R.string.home,
        route = Screen.LandingPage.route
    )

    object ViewsStatsPage : TopNavItem(
        page = R.string.view_stats,
        route = Screen.ViewsStatsPage.route
    )

    object SongsStatsPage : TopNavItem(
        page = R.string.song_stats,
        route = Screen.SongsStatsPage.route
    )

    object LikesPage : TopNavItem(
        page = R.string.likes,
        route = Screen.LibraryPage.route
    )

    object PlaylistsPage : TopNavItem(
        page = R.string.playlists,
        route = Screen.PlaylistsPage.route
    )

    object ArtistsPage : TopNavItem(
        page = R.string.artists,
        route = Screen.ArtistsPage.route
    )
}
