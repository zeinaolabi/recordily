package com.example.recordily_client.pages.artist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.recordily_client.view_models.SongViewModel
import com.example.recordily_client.view_models.UnreleasedSongsViewModel
import kotlinx.coroutines.launch

private const val limit = 40

@ExperimentalAnimationApi
@Composable
fun UnreleasedSongsPage(navController: NavController){
    val unreleasedSongsViewModel: UnreleasedSongsViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    unreleasedSongsViewModel.getUnreleasedSongs(token, limit)
    val unreleasedSongs by unreleasedSongsViewModel.unreleasedSongsResultLiveData.observeAsState()

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

                UnreleasedSongsContent(
                    navController,
                    unreleasedSongs,
                    unreleasedSongsViewModel,
                    token
                )
            }
        }
    }
}

@Composable
private fun UnreleasedSongsContent(
    navController: NavController,
    unreleasedSongs: List<SongResponse>?,
    unreleasedSongsViewModel: UnreleasedSongsViewModel,
    token: String
){
    val coroutinesScope = rememberCoroutineScope()
    val songViewModel: SongViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(ScrollState(0))
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        when {
            unreleasedSongs === null ->
                Row(modifier = Modifier.fillMaxSize()) {
                    CircularProgressBar()
                }
            unreleasedSongs.isEmpty() -> EmptyState(stringResource(id = R.string.no_songs_found))
            else -> {
                for (song in unreleasedSongs) {
                    UnreleasedSongCard(
                        song = song,
                        onSongClick = {
                            songViewModel.clearQueue()
                            for (queueSong in unreleasedSongs) {
                                songViewModel.updateQueue(queueSong.id)
                            }

                            navigateTo(
                                navController = navController,
                                destination = Screen.SongPage.route,
                                popUpTo = Screen.UnreleasedSongsPage.route
                            )
                        },
                        onUploadClick = {
                            coroutinesScope.launch {
                                unreleasedSongsViewModel.publishSong(token, song.id)
                                unreleasedSongsViewModel.getUnreleasedSongs(token, limit)
                            }
                        }
                    )
                }
            }
        }
    }
}