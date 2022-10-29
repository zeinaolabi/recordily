package com.example.recordily_client.navigation

import androidx.annotation.StringRes
import com.example.recordily_client.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: Int
) {
    object Home : BottomNavItem(
        route = Screen.CommonLandingPage.route,
        titleResId = R.string.home,
        icon = R.drawable.deviation_icon
    )

    object Search : BottomNavItem(
        route = Screen.CommonSearchPage.route,
        titleResId = R.string.search,
        icon = R.drawable.search_icon
    )

    object Live : BottomNavItem(
        route = Screen.CommonLiveEventsPage.route,
        titleResId = R.string.live,
        icon = R.drawable.live_icon
    )

    object Profile : BottomNavItem(
        route = Screen.LoginPage.route,
        titleResId = R.string.signin,
        icon = R.drawable.music_library_icon
    )
}