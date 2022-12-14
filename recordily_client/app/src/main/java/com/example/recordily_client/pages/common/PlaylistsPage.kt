package com.example.recordily_client.pages.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.PlaylistsViewModel

private val searchInput = mutableStateOf("")

@Composable
fun PlaylistsPage(navController: NavController){
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()
    val playlistsViewModel: PlaylistsViewModel = viewModel()

    playlistsViewModel.getPlaylists(token)

    val playlists = playlistsViewModel.playlistResultLiveData.observeAsState()

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
            PlaylistsPageContent(navController, playlists.value, playlistsViewModel, token)
        }

        if(searchInput.value == "") {
            FloatingButton(
                onClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.CreatePlaylistPage.route,
                        popUpTo = Screen.PlaylistPage.route
                    )
                }
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
private fun PlaylistsPageContent(
    navController: NavController,
    playlists: List<PlaylistResponse>?,
    playlistsViewModel: PlaylistsViewModel,
    token: String
){
    val searchResult by playlistsViewModel.searchForPlaylistResultLiveData.observeAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        if(searchInput.value == ""){
            Playlists(navController, playlists)
        }
        else{
            playlistsViewModel.searchForPlaylist(token, searchInput.value)
            SearchResult(navController, searchResult)
        }

    }
}

@Composable
private fun Playlists(navController: NavController, playlists: List<PlaylistResponse>?){
    val pageOptions = listOf(
        TopNavItem.LikesPage, TopNavItem.PlaylistsPage, TopNavItem.ArtistsPage
    )

    TopNavBar(
        pageOptions = pageOptions,
        currentPage = R.string.playlists,
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
            playlists === null ->
                Row(modifier = Modifier.fillMaxSize()) {
                    CircularProgressBar()
                }
            playlists.isEmpty() -> EmptyState(stringResource(id = R.string.no_songs_found))
            else -> {
                for (playlist in playlists) {
                    PlaylistCard(
                        playlist = playlist,
                        onPlaylistClick = {
                            navigateTo(
                                navController = navController,
                                destination = Screen.PlaylistPage.route + '/' + playlist.id.toString(),
                                popUpTo = Screen.PlaylistsPage.route
                            )
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
private fun SearchResult(navController: NavController, playlists: List<PlaylistResponse>?){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding( dimensionResource(id = R.dimen.padding_medium))
    ){
        when {
            playlists === null ->
                Row(modifier = Modifier.fillMaxSize()) {
                    CircularProgressBar()
                }
            playlists.isEmpty() -> EmptyState(stringResource(id = R.string.no_songs_found))
            else -> {
                for (playlist in playlists) {
                    PlaylistCard(
                        playlist = playlist,
                        onPlaylistClick = {
                            navigateTo(
                                navController = navController,
                                destination = Screen.PlaylistPage.route + '/' + playlist.id.toString(),
                                popUpTo = Screen.PlaylistsPage.route
                            )
                        }
                    )
                }
            }
        }
    }
}



