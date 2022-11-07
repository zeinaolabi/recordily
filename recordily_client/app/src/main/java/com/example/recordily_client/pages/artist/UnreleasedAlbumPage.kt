package com.example.recordily_client.pages.artist

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.recordily_client.responses.AlbumResponse

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun UnreleasedAlbumPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar(navController, stringResource(id = R.string.album))

            AlbumHeader(AlbumResponse("", 0, "", "", "", 0, ""))

            HorizontalLine()

            UnreleasedAlbumContent(navController)

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
private fun UnreleasedAlbumContent(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        for(i in 1..3){
            UnreleasedAlbumSongCard(
                onSongClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.SongPage.route,
                        popUpTo = Screen.UnreleasedAlbumsPage.route
                    )
                },
                onDeleteClick = {
                    //Delete Song from album
                }
            )
        }
    }
}
