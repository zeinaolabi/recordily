package com.example.recordily_client.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.res.stringResource
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: Int
) {
    object Home : BottomNavItem(
        route = Screen.CommonLandingPage.route,
        titleResId = R.string.signin,
        icon = R.drawable.deviation_icon
    )

    object Search : BottomNavItem(
        route = Screen.CommonSearchPage.route,
        titleResId = R.string.signin,
        icon = R.drawable.search_icon
    )

    object Live : BottomNavItem(
        route = Screen.LoginPage.route,
        titleResId = R.string.signin,
        icon = R.drawable.live_icon
    )

    object Profile : BottomNavItem(
        route = Screen.LoginPage.route,
        titleResId = R.string.signin,
        icon = R.drawable.music_library_icon
    )
}