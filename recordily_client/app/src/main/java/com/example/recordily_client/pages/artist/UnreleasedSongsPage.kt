package com.example.recordily_client.pages.artist

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
import com.example.recordily_client.components.SongCard
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun UnreleasedSongsPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            ExitBar(
                navController = navController,
                title = stringResource(id = R.string.unreleased_songs)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            ){
                HorizontalLine()

                UnreleasedSongsContent(navController)
            }
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
private fun UnreleasedSongsContent(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        for(i in 1..3){
            UnreleasedSongCard(
                song = SongResponse(1, "", 1, "", "", "", 1, 1, "", "", 1, ""),
                onSongClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.SongPage.route,
                        popUpTo = Screen.UnreleasedSongsPage.route
                    )
                },
                onUploadClick = {
                    //Upload Song
                }
            )
        }

    }
}