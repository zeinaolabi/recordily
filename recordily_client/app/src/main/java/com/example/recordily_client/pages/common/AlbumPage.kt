package com.example.recordily_client.pages.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.AlbumViewModel
import com.example.recordily_client.view_models.AlbumsViewModel
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.PlaylistViewModel

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun AlbumPage(navController: NavController, album_id: String){
    val loginViewModel: LoginViewModel = viewModel()
    val albumViewModel: AlbumViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    albumViewModel.getAlbumInfo(token, album_id)

    val album by albumViewModel.albumInfoResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar(navController, stringResource(id = R.string.album))

            album?.let { AlbumHeader(it) }

            HorizontalLine()

            AlbumPageContent(navController)

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
private fun AlbumPageContent(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        for(i in 1..3){
            SongCard(
                song = SongResponse(1,"",1,"","","",1,
                    1,"","",1,""),
                onSongClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.SongPage.route,
                        popUpTo = Screen.AlbumPage.route
                    )
                },
                onMoreClick = { popUpVisibility.value = true }
            )
        }
    }
}
