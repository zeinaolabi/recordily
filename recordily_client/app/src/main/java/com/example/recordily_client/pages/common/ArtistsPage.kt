package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import com.example.recordily_client.responses.ArtistResponse
import com.example.recordily_client.view_models.ArtistsViewModel
import com.example.recordily_client.view_models.LoginViewModel

private val searchInput = mutableStateOf("")

@Composable
fun ArtistsPage(navController: NavController) {
    val artistsViewModel: ArtistsViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    artistsViewModel.getFollowedArtists(token)
    val followedArtistsResult = artistsViewModel.followedArtistsResultLiveData.observeAsState()

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                followedArtistsResult.value?.let { it -> ArtistsPageContent(navController, it) }
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun ArtistsPageContent(navController: NavController, artists: List<ArtistResponse>){
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
            currentPage = R.string.artists,
            navController = navController
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        for(artist in artists){
            ArtistCard(
                artist = artist,
                onClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.ArtistProfilePage.route + '/' + artist.id.toString(),
                        popUpTo = Screen.ArtistsPage.route
                    )
                }
            )
        }
    }
}