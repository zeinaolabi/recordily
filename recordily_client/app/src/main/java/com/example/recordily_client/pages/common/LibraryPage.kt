package com.example.recordily_client.pages.common

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.LikesPageViewModel
import com.example.recordily_client.view_models.LoginViewModel

private val searchInput = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun LibraryPage(navController: NavController){
    val loginViewModel: LoginViewModel = viewModel()
    val likesPageViewModel: LikesPageViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    likesPageViewModel.getLikedSongs(token)

    val songsLiked = likesPageViewModel.likedSongsResultLiveData.observeAsState()

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
            LibraryPageContent(navController, songsLiked.value, token, likesPageViewModel)
        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Popup(
                popUpVisibility = popUpVisibility,
                isPlaylist = false
            )
        }
    }
}

@Composable
private fun LibraryPageContent(navController: NavController, songsLiked: List<SongResponse>?, token: String, likesPageViewModel: LikesPageViewModel){
    val searchResult = likesPageViewModel.likedSongsResultLiveData.observeAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        if(searchInput.value == ""){
            LikedSongs(navController, songsLiked)
        } else {
            likesPageViewModel.searchLikedSongs(token, searchInput.value)
            SearchResult(navController, searchResult.value)
        }

    }
}

@Composable
private fun LikedSongs(navController: NavController, songsLiked: List<SongResponse>?){
    val pageOptions = listOf(
        TopNavItem.LikesPage, TopNavItem.PlaylistsPage, TopNavItem.ArtistsPage
    )

    TopNavBar(
        pageOptions = pageOptions,
        currentPage = R.string.likes,
        navController = navController
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal =dimensionResource(id = R.dimen.padding_medium))
            .fillMaxHeight(.85f)
            .verticalScroll(ScrollState(0))
    ){

        if(songsLiked == null || songsLiked.isEmpty()){
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
                    text = "No Songs found",
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        else{
            for(song in songsLiked){
                Log.i("song", song.toString())
                SongCard(
                    song = song,
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route,
                            popUpTo = Screen.LibraryPage.route
                        )
                    },
                    onMoreClick = {
                        popUpVisibility.value = true
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
private fun SearchResult(navController: NavController, songsLiked: List<SongResponse>?){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding( dimensionResource(id = R.dimen.padding_medium))
    ){
        if (songsLiked != null) {
            for(song in songsLiked){
                SongCard(
                    song = song,
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route,
                            popUpTo = Screen.LibraryPage.route
                        )
                    },
                    onMoreClick = {
                        popUpVisibility.value = true
                    }
                )
            }
        }

    }
}