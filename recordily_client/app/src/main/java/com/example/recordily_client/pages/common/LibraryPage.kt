package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.recordily_client.view_models.LikesPageViewModel
import com.example.recordily_client.view_models.SongViewModel

private val searchInput = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@Composable
fun LibraryPage(navController: NavController){
    val likesPageViewModel: LikesPageViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    likesPageViewModel.getLikedSongs(token)
    val songsLiked = likesPageViewModel.likedSongsResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_large))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            LibraryPageContent(navController, songsLiked.value, token, likesPageViewModel)
        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
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

@Composable
private fun LibraryPageContent(
    navController: NavController,
    songsLiked: List<SongResponse>?,
    token: String,
    likesPageViewModel: LikesPageViewModel
){
    val searchResult = likesPageViewModel.searchResultLiveData.observeAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        if(searchInput.value == ""){
            LikedSongs(navController, songsLiked)
        } else {
            likesPageViewModel.searchLikedSongs(token, searchInput.value)
            SearchResult(navController, searchResult.value)
        }

    }
}

@Composable
private fun LikedSongs(navController: NavController, songsLiked: List<SongResponse>?){
    val pageOptions = listOf(
        TopNavItem.LikesPage, TopNavItem.PlaylistsPage, TopNavItem.ArtistsPage
    )
    val songViewModel: SongViewModel = viewModel()

    TopNavBar(
        pageOptions = pageOptions,
        currentPage = R.string.likes,
        navController = navController
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .fillMaxHeight(.85f)
            .verticalScroll(ScrollState(0))
    ){

        when {
            songsLiked === null ->
                Row(modifier = Modifier.fillMaxSize()){
                    CircularProgressBar()
                }
            songsLiked.isEmpty() -> EmptyState(stringResource(id = R.string.no_songs_found))
            else -> {
                for (song in songsLiked) {
                    SongCard(
                        song = song,
                        onSongClick = {
                            songViewModel.clearQueue()
                            for (queueSong in songsLiked) {
                                songViewModel.updateQueue(queueSong.id)
                            }

                            navigateTo(
                                navController = navController,
                                destination = Screen.SongPage.route + '/' + song.id.toString(),
                                popUpTo = Screen.LibraryPage.route
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

    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.Bottom
    ){
        BottomNavigationBar(navController)
    }
}

@Composable
private fun SearchResult(navController: NavController, songsLiked: List<SongResponse>?){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding( dimensionResource(id = R.dimen.padding_medium))
    ){

        when {
            songsLiked === null ->
                Row(modifier = Modifier.fillMaxSize()){
                    CircularProgressBar()
                }
            songsLiked.isEmpty() -> EmptyState(stringResource(id = R.string.no_songs_found))
            else -> {
                for (song in songsLiked) {
                    SongCard(
                        song = song,
                        onSongClick = {
                            navigateTo(
                                navController = navController,
                                destination = Screen.SongPage.route + '/' + song.id.toString(),
                                popUpTo = Screen.LibraryPage.route
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