package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.Header
import com.example.recordily_client.components.SongsBox
import com.example.recordily_client.components.TopNavBar
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen

@Composable
fun CommonLandingPage(navController: NavController){
    val home = Destination(stringResource(R.string.home), Screen.CommonLandingPage.route)
    val viewStats = Destination(stringResource(R.string.view_stats), Screen.ViewsStatsPage.route)
    val songStats = Destination(stringResource(R.string.song_stats), Screen.CommonLandingPage.route)
    val pageOptions = listOf(
        home, viewStats, songStats
    )

    Scaffold(
        topBar = { Header(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopNavBar(
                pageOptions = pageOptions,
                currentPage = "Home", navController = navController
            )

            SongsBox("Suggested Songs")
        }
    }

}