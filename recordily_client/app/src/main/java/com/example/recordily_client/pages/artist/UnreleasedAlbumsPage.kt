package com.example.recordily_client.pages.artist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.UnreleasedAlbumsViewModel
import kotlinx.coroutines.launch

private const val limit = 40

@ExperimentalAnimationApi
@Composable
fun UnreleasedAlbumsPage(navController: NavController){
    val unreleasedAlbumsViewModel: UnreleasedAlbumsViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    unreleasedAlbumsViewModel.getUnreleasedSongs(token, limit)

    val unreleasedAlbums by unreleasedAlbumsViewModel.unreleasedAlbumsResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            ExitBar(
                navController = navController,
                title = stringResource(id = R.string.unreleased_albums)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            ){
                HorizontalLine()

                UnreleasedAlbumsContent(navController, unreleasedAlbums, unreleasedAlbumsViewModel, token)
            }
        }
    }
}

@Composable
private fun UnreleasedAlbumsContent(
    navController: NavController,
    unreleasedAlbums: List<AlbumResponse>?,
    viewModel: UnreleasedAlbumsViewModel,
    token: String
){
    val coroutinesScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(ScrollState(0)),
    ){
        if(unreleasedAlbums == null || unreleasedAlbums.isEmpty()){
            EmptyState(message = stringResource(id = R.string.no_albums_found))
        }
        else {
            for (album in unreleasedAlbums) {
                UnreleasedAlbumCard(
                    album = album,
                    navController = navController,
                    onUploadClick = {
                        coroutinesScope.launch {
                            viewModel.publishAlbum(token, album.id)
                            viewModel.getUnreleasedSongs(token, limit)
                        }
                    }
                )
            }
        }
    }
}