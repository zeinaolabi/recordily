package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.view_models.AlbumsViewModel
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.PlaylistViewModel

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun AlbumsPage(navController: NavController, artist_id: String){
    val limit = 40;
    val loginViewModel: LoginViewModel = viewModel()
    val albumViewModel: AlbumsViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    albumViewModel.getAlbums(token, artist_id, limit)

    val albums = albumViewModel.albumsResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            ExitBar(
                navController = navController,
                title = stringResource(id = R.string.albums)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            ){
                HorizontalLine()

                AlbumsPageContent(navController, albums.value)
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
private fun AlbumsPageContent(navController: NavController, albums: List<AlbumResponse>?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        if (albums != null) {
            for(album in albums){
                AlbumCard(
                    album = album,
                    onAlbumClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.AlbumPage.route,
                            popUpTo = Screen.ArtistProfilePage.route
                        )
                    }
                )
            }
        }

    }
}