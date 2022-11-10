package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.ProfileViewModel

private val popUpVisibility = mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@ExperimentalAnimationApi
@Composable
fun CommonProfilePage(navController: NavController){
    val limit = 3
    val pageOptions = listOf(
        TopNavItem.ProfilePage, TopNavItem.UnreleasedPage
    )
    val loginViewModel: LoginViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    profileViewModel.getInfo(token)
    profileViewModel.getTopSongs(token, limit)
    profileViewModel.getRecentlyPlayed(token, limit)
    profileViewModel.getPlaylists(token, limit)

    val profile by profileViewModel.userInfoResultLiveData.observeAsState()
    val topSongs by profileViewModel.topSongsResultLiveData.observeAsState()
    val recentlyPlayedSongs by profileViewModel.recentlyPlayedResultLiveData.observeAsState()
    val playlists by profileViewModel.playlistsResultResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar( navController, stringResource(id = R.string.profile))

            profile?.let {
                ProfileHeader(navController, it)

                if (loginViewModel.sharedPreferences.getInt("user_type_id", -1) == 0) {
                    TopNavBar(
                        pageOptions = pageOptions,
                        currentPage = R.string.profile,
                        navController = navController
                    )
                } else {
                    HorizontalLine()
                }

                ProfileContentColumn(navController, topSongs, recentlyPlayedSongs, playlists)
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
                isPlaylist = false
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
private fun ProfileContentColumn(
    navController: NavController,
    topSongs: List<SongResponse>?,
    recentlyPlayedSongs: List<SongResponse>?,
    playlists: List<PlaylistResponse>?
){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        SongsCards(
            title = stringResource(id = R.string.top_songs),
            songs = topSongs,
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.TopSongsPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
            },
            songID = songID
        )

        SongsCards(
            title = stringResource(id = R.string.recently_played),
            songs = recentlyPlayedSongs,
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.RecentlyPlayedSongsPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
            },
            songID = songID
        )

        PlaylistsCard(
            title = stringResource(id = R.string.playlists),
            playlists = playlists,
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.PlaylistsPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            },
            onPlaylistClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.PlaylistPage.route,
                    popUpTo = Screen.ProfilePage.route
                )
            }
        )
    }
}