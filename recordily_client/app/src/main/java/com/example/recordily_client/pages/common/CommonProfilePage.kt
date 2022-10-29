package com.example.recordily_client.pages.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.ProfileHeader
import com.example.recordily_client.components.SongsCards
import com.example.recordily_client.components.TopNavBar
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.Destination

@Composable
fun CommonProfilePage(navController: NavController){
    val profile = Destination(stringResource(R.string.profile), Screen.CommonProfilePage.route)
    val unreleased = Destination(stringResource(R.string.unreleased), Screen.UnreleasedPage.route)
    val pageOptions = listOf(
        profile, unreleased
    )

    Scaffold(
        topBar = { ProfileHeader(navController) },
        bottomBar = { BottomNavigationBar(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = "Profile",
                    navController = navController
                )

                ProfileContentColumn(navController)
            }
        }
    )
}

@Composable
fun ProfileContentColumn(navController: NavController){
    Column(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .verticalScroll(rememberScrollState())
    ){
        SongsCards("Top Songs", navController, Screen.CommonLandingPage.route)
        SongsCards("Recently Played", navController, Screen.CommonLandingPage.route)
        SongsCards("Playlists", navController, Screen.CommonLandingPage.route)
    }
}