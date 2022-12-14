package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.SearchTextField
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SearchResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.SearchPageViewModel
import com.example.recordily_client.view_models.SongViewModel
import kotlinx.coroutines.launch

private val searchInput = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@Composable
fun CommonSearchPage(navController: NavController){
    val limit = 3
    val searchPageViewModel: SearchPageViewModel = viewModel()
    val coroutinesScope = rememberCoroutineScope()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()
    val songs = mutableStateListOf<SongResponse>()

    coroutinesScope.launch{
        val suggestedSongs = searchPageViewModel.getSuggestedResult(token, limit)
        for(suggestedSong in suggestedSongs){
            songs.add(suggestedSong)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        SearchPageLayout(navController, searchPageViewModel, token, songs)
    }

    DisposableEffect(Unit) {
        onDispose {
            searchInput.value = ""
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun SearchPageLayout(
    navController: NavController,
    searchPageViewModel: SearchPageViewModel,
    token: String,
    suggestedResult: List<SongResponse>
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_large),
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ){
            SearchPageContent(navController, searchPageViewModel, token, suggestedResult)
        }

        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ){
            BottomNavigationBar(navController)
        }
    }

    AnimatedVisibility(
        visible = popUpVisibility.value,
        enter = expandVertically(expandFrom = Alignment.CenterVertically),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
    ) {
        Popup(
            songID = songID.value,
            popUpVisibility = popUpVisibility,
            playlistPopUpVisibility = playlistPopUpVisibility,
            playlistID = null
        )
    }

    AnimatedVisibility(
        visible = playlistPopUpVisibility.value,
        enter = expandVertically(expandFrom = Alignment.CenterVertically),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
    ) {
        PlaylistPopup(
            songID = songID.value,
            popUpVisibility = playlistPopUpVisibility
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun SearchPageContent(
    navController: NavController,
    searchPageViewModel: SearchPageViewModel,
    token: String,
    suggestedResult: List<SongResponse>
){
    val searchResult by searchPageViewModel.searchResultLiveData.observeAsState()

    Text(
        text = stringResource(id = R.string.discover_songs),
        fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary,
    )

    SearchTextField(searchInput)

    if (searchInput.value == "") {
        SuggestedContent(navController, suggestedResult)
    } else {
        searchPageViewModel.getSearchResult(token, searchInput.value)
        SearchResultContent(navController, searchResult)
    }
}

@Composable
private fun SuggestedContent(navController: NavController, data: List<SongResponse>?){
    SongsCards(
        title = stringResource(id = R.string.suggested),
        songs = data,
        navController = navController,
        destination = {
            navigateTo(
                navController = navController,
                destination = Screen.SuggestedSongsPage.route,
                popUpTo = Screen.SearchPage.route
            )
        },
        onMoreClick = { popUpVisibility.value = true },
        songID = songID
    )
}

@Composable
private fun SearchResultContent(navController: NavController, data: SearchResponse?){
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ){
        when{
            data == null -> CircularProgressBar()
            data.artists.isEmpty() && data.songs.isEmpty() ->
                EmptyState(message = stringResource(id = R.string.no_results))
            else -> ResultData(navController, data)
        }
    }
}

@Composable
private fun ResultData(navController: NavController, data: SearchResponse){
    val songViewModel: SongViewModel = viewModel()

    for(artist in data.artists){
        ArtistCard(
            artist = artist,
            onClick = {
                navigateTo(
                    navController = navController,
                    destination = Screen.ArtistProfilePage.route + '/' + artist.id,
                    popUpTo = Screen.SearchPage.route
                )
            }
        )
    }

    for(song in data.songs){
        SongCard(
            song = song,
            onSongClick = {
                songViewModel.clearQueue()
                for(queueSong in data.songs){
                    songViewModel.updateQueue(queueSong.id)
                }

                navigateTo(
                    navController = navController,
                    destination = Screen.SongPage.route + '/' + song.id,
                    popUpTo = Screen.PlaylistPage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
                songID.value = song.id
            }
        )
    }
}
