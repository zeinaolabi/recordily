package com.example.recordily_client.pages.artist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen

@Composable
fun UnreleasedPage(navController: NavController) {
    val profile = Destination(stringResource(R.string.profile), Screen.CommonProfilePage.route)
    val unreleased = Destination(stringResource(R.string.unreleased), Screen.CommonProfilePage.route)
    val pageOptions = listOf(
        profile, unreleased
    )

    Scaffold(
    topBar = { ProfileHeader(navController) },
    bottomBar = { BottomNavigationBar(navController) }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TopNavBar(
                pageOptions = pageOptions,
                currentPage = "Unreleased",
                navController = navController
            )

            Column(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_large))
                    .verticalScroll(ScrollState(0))
            ){
                SongsCards("Unreleased Songs", navController)
                SongsCards("Unreleased Albums", navController)
            }
        }
    }
}