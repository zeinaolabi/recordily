package com.example.recordily_client.pages.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.ArtistProfileViewModel
import com.example.recordily_client.view_models.LoginViewModel

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun ArtistProfilePage(navController: NavController, artist_id: String){
    val artistProfileViewModel: ArtistProfileViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    artistProfileViewModel.getArtistFollowers(token, artist_id)
    artistProfileViewModel.getArtist(token, artist_id)
    artistProfileViewModel.isFollowed(token, artist_id)

    val artistInfo = artistProfileViewModel.artistInfoResultLiveData.observeAsState()
    val artistFollowers = artistProfileViewModel.artistFollowersResultLiveData.observeAsState()
    val isFollowed = artistProfileViewModel.isFollowedResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar(navController, stringResource(id = R.string.artist))

            if(artistInfo.value != null && artistFollowers.value != null && isFollowed.value != null){
                ArtistPageHeader(artistInfo.value!!, artistFollowers.value!!, isFollowed.value!!, token)
            }

            HorizontalLine()

            ArtistProfileContent(navController)

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
private fun ArtistProfileContent(navController: NavController){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        AlbumsBox(
            title = stringResource(id = R.string.top_albums),
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.AlbumPage.route,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            }
        )

        AlbumsCards(
            title = stringResource(id = R.string.albums),
            buttonDestination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.AlbumsPage.route,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            },
            onAlbumClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.AlbumPage.route,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            }
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_songs),
            navController = navController,
            data = null
        )

        SongsCards(
            title = stringResource(id = R.string.songs),
            data = listOf(SongResponse(1,"",1,"","","",1,
                1,"","",1,"")
            ),
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.ArtistSongsPage.route,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
            }
        )
    }
}
