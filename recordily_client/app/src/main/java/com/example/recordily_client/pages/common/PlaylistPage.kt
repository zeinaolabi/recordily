package com.example.recordily_client.pages.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun PlaylistPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar(navController, stringResource(id = R.string.playlists))

            PlaylistHeader(navController)

            HorizontalLine()

            PlaylistPageContent(navController)

        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Popup(
                popUpVisibility = popUpVisibility,
                isPlaylist = true
            )
        }
    }
}

@Composable
private fun PlaylistPageContent(navController: NavController){
    Column(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        for(i in 1..3){
            SongCard(
                onSongClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.SongPage.route,
                        popUpTo = Screen.PlaylistPage.route
                    )
                },
                onMoreClick = {
                    popUpVisibility.value = true
                }
            )
        }
    }
}
