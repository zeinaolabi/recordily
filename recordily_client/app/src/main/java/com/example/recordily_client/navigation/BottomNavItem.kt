package com.example.recordily_client.navigation

import androidx.annotation.StringRes
import com.example.recordily_client.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: Int
) {
    object Home : BottomNavItem(
        route = Screen.LandingPage.route,
        titleResId = R.string.home,
        icon = R.drawable.deviation_icon
    )

    object Search : BottomNavItem(
        route = Screen.SearchPage.route,
        titleResId = R.string.search,
        icon = R.drawable.search_icon
    )

    object Live : BottomNavItem(
        route = Screen.LiveEventsPage.route,
        titleResId = R.string.live,
        icon = R.drawable.live_icon
    )

    object Profile : BottomNavItem(
        route = Screen.LibraryPage.route,
        titleResId = R.string.signin,
        icon = R.drawable.music_library_icon
    )
}