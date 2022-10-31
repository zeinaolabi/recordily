package com.example.recordily_client.pages.artist

import androidx.compose.foundation.layout.*
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
import com.example.recordily_client.navigation.navigateTo

@Composable
fun UnreleasedPage(navController: NavController) {
    val profile = Destination(stringResource(R.string.profile), Screen.ProfilePage.route)
    val unreleased = Destination(stringResource(R.string.unreleased), Screen.UnreleasedPage.route)
    val pageOptions = listOf(
        profile, unreleased
    )

    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.profile)) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                ProfileHeader(navController)

                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = "Unreleased",
                    navController = navController
                )

                AddMusicRow(navController)

                UnreleasedContentColumn(navController)
            }
        }
    )
}

@Composable
fun AddMusicRow(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        MediumRoundButton(
            text = stringResource(id = R.string.record),
            onClick = {
                navController.navigate(Screen.RecordPage.route) {

                    popUpTo(Screen.ProfilePage.route) {
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        MediumRoundButton(
            text = stringResource(id = R.string.upload_song),
            onClick = {
                navController.navigate(Screen.UploadSongPage.route) {

                    popUpTo(Screen.ProfilePage.route) {
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        MediumRoundButton(
            text = stringResource(id = R.string.upload_album),
            onClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UploadAlbumPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            }
        )
    }
}

@Composable
fun UnreleasedContentColumn(navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ){
        UnreleasedSongsCard(
            title = stringResource(id = R.string.unreleased),
            destination = {
                       navigateTo(
                           navController = navController,
                           destination = Screen.UnreleasedSongsPage.route,
                           popUpTo = Screen.UnreleasedPage.route
                       )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            },
            onUploadClick = {
                //Upload Song
            }
        )

        AlbumsCards(
            title = "Unreleased Albums",
            buttonDestination = {},
            onAlbumClick = {} ,
        )
    }
}