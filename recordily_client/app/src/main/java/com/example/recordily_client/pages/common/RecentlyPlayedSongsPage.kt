package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import com.example.recordily_client.view_models.RecentlyPlaySongsViewModel
import com.example.recordily_client.view_models.SongViewModel

private val popUpVisibility = mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@ExperimentalAnimationApi
@Composable
fun RecentlyPlayedSongsPage(navController: NavController){
    val limit = 40
    val recentlyPlayedViewModel: RecentlyPlaySongsViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    recentlyPlayedViewModel.getRecentlyPlayed(token, limit)
    val songs by recentlyPlayedViewModel.recentlyPlayResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            ExitBar(
                navController = navController,
                title = stringResource(id = R.string.recently_played)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            ){
                HorizontalLine()

                RecentlyPlayedSongsContent(navController, songs)
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
private fun RecentlyPlayedSongsContent(navController: NavController, songs: List<SongResponse>?){
    val songViewModel: SongViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0))
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        when {
            songs === null ->
                Row(modifier = Modifier.fillMaxSize()) {
                    CircularProgressBar()
                }
            songs.isEmpty() -> EmptyState(stringResource(id = R.string.no_songs_found))
            else -> {
                for (song in songs) {
                    SongCard(
                        song = song,
                        onSongClick = {
                            songViewModel.clearQueue()
                            for (queueSong in songs) {
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
    }
}