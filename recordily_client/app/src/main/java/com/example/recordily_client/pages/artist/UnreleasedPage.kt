package com.example.recordily_client.pages.artist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.ProfileViewModel

@Composable
fun UnreleasedPage(navController: NavController) {
    val pageOptions = listOf(
        TopNavItem.ProfilePage, TopNavItem.UnreleasedPage
    )
    val loginViewModel: LoginViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    profileViewModel.getUserInfo(token)

    val profile by profileViewModel.userInfoResultLiveData.observeAsState()

    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.profile)) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                profile?.let { it1 -> ProfileHeader(navController, it1) }

                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = R.string.unreleased,
                    navController = navController
                )

                AddMusicRow(navController)

                UnreleasedContentColumn(navController)
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
private fun UnreleasedContentColumn(navController: NavController){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        ){
        UnreleasedSongsCard(
            title = stringResource(id = R.string.unreleased_songs),
            destination = {
                       navigateTo(
                           navController = navController,
                           destination = Screen.UnreleasedSongsPage.route,
                           popUpTo = Screen.UnreleasedPage.route
                       )
            },
            onSongClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            },
            onUploadClick = {
                //Upload Song
            }
        )

        UnreleasedAlbumsCard(
            title = stringResource(id = R.string.unreleased_albums),
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UnreleasedAlbumsPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            },
            onAlbumClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.UnreleasedAlbumPage.route,
                    popUpTo = Screen.UnreleasedPage.route
                )
            },
            onUploadClick = {
                //Upload Song
            }
        )
    }
}