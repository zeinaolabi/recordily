package com.example.recordily_client.pages.common

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.recordily_client.responses.AlbumResponse
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.ArtistProfileViewModel
import com.example.recordily_client.view_models.LoginViewModel

private val popUpVisibility = mutableStateOf(false)
private val playlistPopUpVisibility = mutableStateOf(false)
private val songID = mutableStateOf(-1)

@ExperimentalAnimationApi
@Composable
fun ArtistProfilePage(navController: NavController, artistID: String){
    val limit = 3
    val topLimit = 5
    val artistProfileViewModel: ArtistProfileViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    artistProfileViewModel.getArtistFollowers(token, artistID)
    artistProfileViewModel.getArtist(token, artistID)
    artistProfileViewModel.isFollowed(token, artistID)
    artistProfileViewModel.getAlbums(token, artistID, limit)
    artistProfileViewModel.getArtistTopSongs(token, artistID, topLimit)
    artistProfileViewModel.getArtistSongs(token, artistID, limit)
    artistProfileViewModel.getArtistTopAlbums(token, artistID, limit)

    val artistInfo by artistProfileViewModel.artistInfoResultLiveData.observeAsState()
    val artistFollowers by artistProfileViewModel.artistFollowersResultLiveData.observeAsState()
    val isFollowed by artistProfileViewModel.isFollowedResultLiveData.observeAsState()
    val albums by artistProfileViewModel.albumsResultLiveData.observeAsState()
    val topSongs by artistProfileViewModel.artistTopSongsResultLiveData.observeAsState()
    val songs by artistProfileViewModel.artistSongsResultLiveData.observeAsState()
    val topAlbums by artistProfileViewModel.artistTopAlbumsResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            ExitBar(navController, stringResource(id = R.string.artist))

            ArtistPageHeader(artistInfo, artistFollowers, isFollowed, token)

            HorizontalLine()

            ArtistProfileContent(navController, albums, topSongs, songs, topAlbums, artistID)
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
}

@Composable
private fun ArtistProfileContent(
    navController: NavController,
    albums: List<AlbumResponse>?,
    topSongs: List<SongResponse>?,
    songs: List<SongResponse>?,
    topAlbums: List<AlbumResponse>?,
    artist_id: String
){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        AlbumsBox(
            title = stringResource(id = R.string.top_albums),
            navController = navController,
            albums = topAlbums
        )

        AlbumsCards(
            title = stringResource(id = R.string.albums),
            albums = albums,
            navController = navController,
            buttonDestination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.AlbumsPage.route + '/' + artist_id,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            }
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_songs),
            navController = navController,
            songs = topSongs
        )

        SongsCards(
            title = stringResource(id = R.string.songs),
            songs = songs,
            navController = navController,
            destination = {
                navigateTo(
                    navController = navController,
                    destination = Screen.ArtistSongsPage.route + '/' + artist_id,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            },
            onMoreClick = {
                popUpVisibility.value = true
            },
            songID = songID
        )
    }
}
