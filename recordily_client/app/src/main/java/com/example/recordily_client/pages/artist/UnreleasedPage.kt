package com.example.recordily_client.pages.artist

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.ProfileViewModel
import com.example.recordily_client.view_models.UnreleasedViewModel

private const val limit = 3

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnreleasedPage(navController: NavController) {
    val pageOptions = listOf(
        TopNavItem.ProfilePage, TopNavItem.UnreleasedPage
    )
    val profileViewModel: ProfileViewModel = viewModel()
    val unreleasedViewModel: UnreleasedViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    profileViewModel.getInfo(token)
    unreleasedViewModel.getUnreleasedSongs(token, limit)
    unreleasedViewModel.getUnreleasedAlbums(token, limit)

    val profile by profileViewModel.userInfoResultLiveData.observeAsState()
    val unreleasedSongs by unreleasedViewModel.unreleasedSongsResultLiveData.observeAsState()
    val unreleasedAlbums by unreleasedViewModel.unreleasedAlbumsResultLiveData.observeAsState()

    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.profile)) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                profile?.let { it ->
                    ProfileHeader(navController, it)

                    TopNavBar(
                        pageOptions = pageOptions,
                        currentPage = R.string.unreleased,
                        navController = navController
                    )

                    AddMusicRow(navController)

                    UnreleasedContentColumn(
                        navController,
                        unreleasedSongs,
                        unreleasedAlbums,
                        unreleasedViewModel,
                        token
                    )
                }
            }
        }
    )
}

@Composable
private fun AddMusicRow(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        MediumRoundButton(
            text = stringResource(id = R.string.record),
            onClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.RecordPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            }
        )

        MediumRoundButton(
            text = stringResource(id = R.string.upload_song),
            onClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UploadSongPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            }
        )

        MediumRoundButton(
            text = stringResource(id = R.string.upload_album),
            onClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UploadAlbumPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            }
        )
    }
}

@Composable
private fun UnreleasedContentColumn(
    navController: NavController,
    unreleasedSongs: List<SongResponse>?,
    unreleasedAlbums: List<AlbumResponse>?,
    unreleasedViewModel: UnreleasedViewModel,
    token: String
){
    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ){
        UnreleasedSongsCard(
            title = stringResource(id = R.string.unreleased_songs),
            songs = unreleasedSongs,
            navController = navController,
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UnreleasedSongsPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            },
            viewModel = unreleasedViewModel,
            token = token,
            onUploadClick = { unreleasedViewModel.getUnreleasedSongs(token, limit) }
        )

        UnreleasedAlbumsCard(
            title = stringResource(id = R.string.unreleased_albums),
            albums = unreleasedAlbums,
            navController = navController,
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UnreleasedAlbumsPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            },
            viewModel = unreleasedViewModel,
            token = token,
            onUploadClick = { unreleasedViewModel.getUnreleasedAlbums(token, limit) }
        )
    }
}