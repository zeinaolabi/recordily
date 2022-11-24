package com.example.recordily_client.pages.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.AlbumsViewModel

@ExperimentalAnimationApi
@Composable
fun AlbumsPage(navController: NavController, artistID: String){
    val limit = 40
    val albumViewModel: AlbumsViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    albumViewModel.getAlbums(token, artistID, limit)
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
    }
}

@Composable
private fun AlbumsPageContent(navController: NavController, albums: List<AlbumResponse>?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        when {
            albums === null ->
                Row(modifier = Modifier.fillMaxSize()){
                    CircularProgressBar()
                }
            albums.isEmpty() -> EmptyState(stringResource(id = R.string.no_albums_found))
            else -> {
                for (album in albums) {
                    AlbumCard(
                        album = album,
                        navController = navController
                    )
                }
            }
        }

    }
}