package com.example.recordily_client.pages.artist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
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
fun UnreleasedPage(navController: NavController) {
    val profile = Destination(stringResource(R.string.profile), Screen.CommonProfilePage.route)
    val unreleased = Destination(stringResource(R.string.unreleased), Screen.CommonProfilePage.route)
    val pageOptions = listOf(
        profile, unreleased
    )

    Scaffold(
    topBar = { ProfileHeader(navController) },
    bottomBar = { BottomNavigationBar(navController) }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TopNavBar(
                pageOptions = pageOptions,
                currentPage = "Unreleased",
                navController = navController
            )

            AddMusicRow(navController)

            UnreleasedContentColumn(navController)
        }
    }
}

@Composable
fun AddMusicRow(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        MediumRoundButton(
            text = stringResource(id = R.string.record),
            onClick = {
                navController.navigate(Screen.RecordPage.route)
            }
        )

        MediumRoundButton(
            text = stringResource(id = R.string.upload_song),
            onClick = {}
        )

        MediumRoundButton(
            text = stringResource(id = R.string.upload_album),
            onClick = {}
        )
    }
}

@Composable
fun UnreleasedContentColumn(navController: NavController){
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ){
        SongsCards("Unreleased Songs", navController, Screen.CommonLandingPage.route)
        SongsCards("Unreleased Albums", navController, Screen.CommonLandingPage.route)
    }
}