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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun ArtistProfilePage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar(navController, stringResource(id = R.string.artist))

            ArtistPageHeader(navController)

            HorizontalLine()

            ArtistProfileContent(navController)

        }

    }
}

@Composable
fun ArtistProfileContent(navController: NavController){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        SongsBox(title = "Top 5 Albums")

        SongsCards(
            title = stringResource(id = R.string.albums),
            navController = navController,
            destination = Screen.CommonLandingPage.route,
            onClick = {}
        )

        SongsBox(title = "Top 5 Songs")

        SongsCards(
            title = stringResource(id = R.string.songs),
            navController = navController,
            destination = Screen.CommonLandingPage.route,
            onClick = {}
        )


    }
}
