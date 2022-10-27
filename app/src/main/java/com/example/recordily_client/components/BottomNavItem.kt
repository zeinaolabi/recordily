package com.example.recordily_client.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen

sealed class BottomNavItem(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = Screen.RegistrationPage.route,
        titleResId = R.string.signup,
        icon = Icons.Default.Menu
    )

    object Search : BottomNavItem(
        route = Screen.LoginPage.route,
        titleResId = R.string.signin,
        icon = Icons.Default.Search
    )

    object Live : BottomNavItem(
        route = Screen.LoginPage.route,
        titleResId = R.string.signin,
        icon = Icons.Default.Info
    )

    object Profile : BottomNavItem(
        route = Screen.LoginPage.route,
        titleResId = R.string.signin,
        icon = Icons.Default.AccountCircle
    )
}