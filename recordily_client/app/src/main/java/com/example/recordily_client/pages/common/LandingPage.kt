package com.example.recordily_client.pages.common

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
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.TopNavItem

@Composable
fun CommonLandingPage(navController: NavController){
    val pageOptions = listOf(
        TopNavItem.HomePage, TopNavItem.ViewsStatsPage, TopNavItem.SongsStatsPage
    )

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = R.string.home,
                    navController = navController
                )

                LandingPageContent(navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun LandingPageContent(navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
    ){
        SongsBox(
            title = stringResource(id = R.string.suggested),
            navController = navController
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_songs),
            navController = navController
        )

        ArtistsBox(
            title = stringResource(id = R.string.top_5_artists),
            navController = navController
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_liked_songs),
            navController = navController
        )

        SongsBox(
            title = stringResource(id = R.string.recently_played),
            navController = navController
        )
    }
}