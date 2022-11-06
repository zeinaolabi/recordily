package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.view_models.LikesPageViewModel
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.PlaylistsViewModel

private val searchInput = mutableStateOf("")

@Composable
fun PlaylistsPage(navController: NavController){
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    val playlistsViewModel: PlaylistsViewModel = viewModel()

    playlistsViewModel.getPlaylists(token)

    val playlists = playlistsViewModel.playlistResultLiveData.observeAsState()

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                PlaylistsPageContent(navController, playlists.value)
            }

            FloatingButton(
                onClick={
                    navController.navigate(Screen.CreatePlaylistPage.route) {

                        popUpTo(Screen.PlaylistPage.route) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun PlaylistsPageContent(navController: NavController, playlists: List<PlaylistResponse>?){
    val pageOptions = listOf(
        TopNavItem.LikesPage, TopNavItem.PlaylistsPage, TopNavItem.ArtistsPage
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        TopNavBar(
            pageOptions = pageOptions,
            currentPage = R.string.playlists,
            navController = navController
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        if (playlists != null) {
            for(playlist in playlists){
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
