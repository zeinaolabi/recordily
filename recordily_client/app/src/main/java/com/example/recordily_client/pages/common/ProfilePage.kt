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
import com.example.recordily_client.navigation.Destination

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun CommonProfilePage(navController: NavController){
    val profile = Destination(stringResource(R.string.profile), Screen.ProfilePage.route)
    val unreleased = Destination(stringResource(R.string.unreleased), Screen.UnreleasedPage.route)
    val pageOptions = listOf(
        profile, unreleased
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
                currentPage = "Profile",
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
fun ProfileContentColumn(navController: NavController){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        SongsCards(
            title = "Top Songs",
            navController,
            destination = {

            },
            onSongClick = {} ,
            onMoreClick = {
                popUpVisibility.value = true
            }
        )

        SongsCards(
            title = "Recently Played",
            navController,
            destination = {

            },
            onSongClick = {

            } ,
            onMoreClick = {
                popUpVisibility.value = true
            }
        )

        SongsCards(
            title = "Playlists",
            navController,
            destination = {

            },
            onSongClick = {

            } ,
            onMoreClick = {
                popUpVisibility.value = true
            }
        )
    }
}