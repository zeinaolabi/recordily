package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo

private val searchInput = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun LibraryPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_large))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            LibraryPageContent(navController)
        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Popup(
                popUpVisibility = popUpVisibility,
                isPlaylist = false
            )
        }
    }
}

@Composable
private fun LibraryPageContent(navController: NavController){
    val likes = Destination(stringResource(R.string.likes), Screen.LibraryPage.route)
    val playlists = Destination(stringResource(R.string.playlists), Screen.PlaylistsPage.route)
    val artists = Destination(stringResource(R.string.artists), Screen.ArtistsPage.route)
    val pageOptions = listOf(
        likes, playlists, artists
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        TopNavBar(
            pageOptions = pageOptions,
            currentPage = "Likes",
            navController = navController
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal =dimensionResource(id = R.dimen.padding_medium))
        ){
            for(i in 1..3){
                SongCard(
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route,
                            popUpTo = Screen.LibraryPage.route
                        )
                    },
                    onMoreClick = {
                        popUpVisibility.value = true
                    }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ){
            BottomNavigationBar(navController)
        }
    }
}