package com.example.recordily_client.pages.artist

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
import androidx.compose.runtime.DisposableEffect
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
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.SongsStatsViewModel

private val searchInput = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@ExperimentalAnimationApi
@Composable
fun SongsStatsPage(navController: NavController){
    val userCredentials: UserCredentials = viewModel()
    val songsStatsViewModel: SongsStatsViewModel = viewModel()
    val token = userCredentials.getToken()

    songsStatsViewModel.getUserSongs(token)

    val songs by songsStatsViewModel.songsResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        songs?.let { SongsStatsContent(navController, it,  songsStatsViewModel, token) }

        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ){
            BottomNavigationBar(navController)
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

    DisposableEffect(Unit) {
        onDispose {
            searchInput.value = ""
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun SongsStatsContent(
    navController: NavController,
    songs: List<SongResponse>,
    songsStatsViewModel: SongsStatsViewModel,
    token: String
){
    val pageOptions = listOf(
        TopNavItem.HomePage, TopNavItem.ViewsStatsPage, TopNavItem.SongsStatsPage
    )
    val searchResult by songsStatsViewModel.searchResultLiveData.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header(navController)

        TopNavBar(
            pageOptions = pageOptions,
            currentPage = R.string.song_stats,
            navController = navController
        )

        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .padding(bottom = dimensionResource(id = R.dimen.padding_very_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            SearchTextField(searchInput)

            if(searchInput.value == ""){
                SongsResult(navController, songs)
            }
            else{
                songsStatsViewModel.searchReleasedSongs(token, searchInput.value)
                SearchResult(navController, searchResult)
            }
        }
    }
}

@Composable
private fun SongsResult(navController: NavController, songs: List<SongResponse>) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        if (songs.isEmpty()) {
            EmptyState(message = stringResource(id = R.string.no_songs_found))
        } else {
            for (song in songs) {
                SongCard(
                    song = song,
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongStatsPage.route + '/' + song.id,
                            popUpTo = Screen.SongsStatsPage.route
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


@Composable
private fun SearchResult(navController: NavController, searchResult: List<SongResponse>?){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        if (searchResult != null) {
            for(song in searchResult){
                SongCard(
                    song = song,
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongStatsPage.route + '/' + song.id.toString(),
                            popUpTo = Screen.SongsStatsPage.route
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