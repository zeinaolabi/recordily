package com.example.recordily_client.pages.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.ArtistsViewModel

private val searchInput = mutableStateOf("")

@Composable
fun ArtistsPage(navController: NavController) {
    val artistsViewModel: ArtistsViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    artistsViewModel.getFollowedArtists(token)
    val followedArtistsResult by artistsViewModel.followedArtistsResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_large))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            ArtistsPageContent(navController, followedArtistsResult, artistsViewModel, token)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            searchInput.value = ""
        }
    }
}

@Composable
private fun ArtistsPageContent(
    navController: NavController,
    artists: List<UserResponse>?,
    artistsViewModel: ArtistsViewModel,
    token: String
){
    val searchResult = artistsViewModel.searchArtistsResultLiveData.observeAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        if(searchInput.value == ""){
            Artists(navController, artists)
        } else {
            artistsViewModel.searchFollowedArtists(token, searchInput.value)
            SearchResult(navController, searchResult.value)
        }

    }
}

@Composable
private fun Artists(navController: NavController, artists: List<UserResponse>?){
    val pageOptions = listOf(
        TopNavItem.LikesPage, TopNavItem.PlaylistsPage, TopNavItem.ArtistsPage
    )

    TopNavBar(
        pageOptions = pageOptions,
        currentPage = R.string.artists,
        navController = navController
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal =dimensionResource(id = R.dimen.padding_medium))
            .fillMaxHeight(.85f)
            .verticalScroll(ScrollState(0))
    ){
        if(artists == null || artists.isEmpty()){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(
                    painter = painterResource(id = R.drawable.nothing_found),
                    contentDescription = "not found",
                    modifier = Modifier.size(60.dp),
                    tint = Color.Unspecified
                )

                Text(
                    text = "No Artists found",
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        else {
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

    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.Bottom
    ){
        BottomNavigationBar(navController)
    }
}

@Composable
private fun SearchResult(navController: NavController, artists: List<UserResponse>?){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding( dimensionResource(id = R.dimen.padding_medium))
    ){
        artists?.let{
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
}