package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.navigation.navigateTo

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun CommonProfilePage(navController: NavController){
    val pageOptions = listOf(
        TopNavItem.ProfilePage, TopNavItem.UnreleasedPage
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar( navController, stringResource(id = R.string.profile))

            ProfileHeader(navController)

            TopNavBar(
                pageOptions = pageOptions,
                currentPage = R.string.profile,
                navController = navController
            )

            ProfileContentColumn(navController)
        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Popup(
                popUpVisibility = popUpVisibility,
                isPlaylist = false
            )
        }
    }
}



@Composable
private fun ProfileContentColumn(navController: NavController){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        SongsCards(
            title = stringResource(id = R.string.top_songs),
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.TopSongsPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
            }
        )

        SongsCards(
            title = stringResource(id = R.string.recently_played),
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.RecentlyPlayedSongsPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
            }
        )

        PlaylistsCard(
            title = stringResource(id = R.string.playlists),
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.PlaylistsPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onPlaylistClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.PlaylistPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            }
        )
    }
}