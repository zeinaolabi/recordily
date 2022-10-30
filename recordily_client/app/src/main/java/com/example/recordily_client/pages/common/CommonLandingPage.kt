package com.example.recordily_client.pages.common

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
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen

@Composable
fun CommonLandingPage(navController: NavController){
    val home = Destination(stringResource(R.string.home), Screen.CommonLandingPage.route)
    val viewStats = Destination(stringResource(R.string.view_stats), Screen.ViewsStatsPage.route)
    val songStats = Destination(stringResource(R.string.song_stats), Screen.SongsStatsPage.route)
    val pageOptions = listOf(
        home, viewStats, songStats
    )

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = "Home", navController = navController
                )

                LandingPageContent(navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
fun LandingPageContent(navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
    ){
        SongsBox("Suggested Songs", navController)
        SongsBox("Top 5 Played Songs", navController)
        ArtistsBox("Top 5 Artists", navController)
        SongsBox("Top 5 Liked Songs", navController)
        SongsBox("Recently Played", navController)
    }
}