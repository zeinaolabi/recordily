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

@Composable
fun CommonLandingPage(navController: NavController){
    val pageOptions = listOf(
        stringResource(R.string.home), stringResource(R.string.view_stats), stringResource(
            R.string.song_stats)
    )

    Scaffold(
        topBar = { Header(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopNavBar(pageOptions = pageOptions, currentPage = "Home")

            SongsBox("Suggested Songs")
        }
    }

}