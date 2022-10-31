package com.example.recordily_client.pages.common

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
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo

private val searchInput = mutableStateOf("")

@Composable
fun PlaylistsPage(navController: NavController){

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                PlaylistsPageContent(navController)

                FloatingButton(
                    onClick={
                        navController.navigate(Screen.CreatePlaylistPage.route) {

                            popUpTo(Screen.PlaylistPage.route) {
                                    saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun PlaylistsPageContent(navController: NavController){
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
            currentPage = "Playlists",
            navController = navController
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        for(i in 1..3){
            PlaylistCard {
                navigateTo(
                    navController = navController,
                    destination = Screen.PlaylistPage.route,
                    popUpTo = Screen.PlaylistsPage.route
                )
            }
        }
    }
}
