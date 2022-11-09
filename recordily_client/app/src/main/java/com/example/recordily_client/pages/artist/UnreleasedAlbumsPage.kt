package com.example.recordily_client.pages.artist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.UnreleasedAlbumsViewModel
import com.example.recordily_client.view_models.UnreleasedSongsViewModel

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun UnreleasedAlbumsPage(navController: NavController){
    val limit = 40
    val loginViewModel: LoginViewModel = viewModel()
    val unreleasedAlbumsViewModel: UnreleasedAlbumsViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

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

                UnreleasedAlbumsContent(navController, unreleasedAlbums)
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
private fun UnreleasedAlbumsContent(navController: NavController, unreleasedAlbums: List<AlbumResponse>?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        if(unreleasedAlbums == null || unreleasedAlbums.isEmpty()){
            EmptyState(message = stringResource(id = R.string.no_albums_found))
        }
        else {
            for (album in unreleasedAlbums) {
                UnreleasedAlbumCard(
                    onAlbumClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.UnreleasedAlbumPage.route,
                            popUpTo = Screen.UnreleasedAlbumsPage.route
                        )
                    },
                    onUploadClick = {
                        //Upload Song
                    }
                )
            }
        }
    }
}