package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.ExitBar
import com.example.recordily_client.components.HorizontalLine
import com.example.recordily_client.components.Popup
import com.example.recordily_client.components.SongCard
import com.example.recordily_client.navigation.Screen

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun SuggestedSongsPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            ExitBar(
                navController = navController,
                title = stringResource(id = R.string.suggested)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            ){
                HorizontalLine()

                SuggestedSongsContent(navController)
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
private fun SuggestedSongsContent(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        for(i in 1..3){
            SongCard(
                onSongClick = {
                    navController.navigate(Screen.SongPage.route) {

                        popUpTo(Screen.SuggestedSongsPage.route) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onMoreClick = { popUpVisibility.value = true }
            )
        }

    }
}