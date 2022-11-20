package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.SongViewModel
import com.example.recordily_client.view_models.SuggestedSongsViewModel
import kotlinx.coroutines.launch

private val popUpVisibility =mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@ExperimentalAnimationApi
@Composable
fun SuggestedSongsPage(navController: NavController){
    val limit = 20
    val suggestedSongsViewModel: SuggestedSongsViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()
    val coroutinesScope = rememberCoroutineScope()
    val songs = mutableStateListOf<SongResponse>()
    
    coroutinesScope.launch{
        val suggestedSongs = suggestedSongsViewModel.getSuggestedSongs(token, limit)
        for(suggestedSong in suggestedSongs){
            songs.add(suggestedSong)
        }
    }

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

                SuggestedSongsContent(navController, songs)
            }
        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Popup(
                songID = songID.value,
                popUpVisibility = popUpVisibility,
                playlistPopUpVisibility = playlistPopUpVisibility,
                playlistID = null
            )
        }

        AnimatedVisibility(
            visible = playlistPopUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            PlaylistPopup(
                songID = songID.value,
                popUpVisibility = playlistPopUpVisibility
            )
        }
    }
}

@Composable
private fun SuggestedSongsContent(navController: NavController, songs: List<SongResponse>){
    val songViewModel: SongViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        for(song in songs){
            SongCard(
                song = song,
                onSongClick = {
                    songViewModel.clearQueue()
                    for(queueSong in songs){
                        songViewModel.updateQueue(queueSong.id)
                    }

                    navigateTo(
                        navController = navController,
                        destination = Screen.SongPage.route + '/' + song.id,
                        popUpTo = Screen.SuggestedSongsPage.route
                    )
                },
                onMoreClick = {
                    popUpVisibility.value = true
                    songID.value = song.id
                }
            )
        }

    }
}