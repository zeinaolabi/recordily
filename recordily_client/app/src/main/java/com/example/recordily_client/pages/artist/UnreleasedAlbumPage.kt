package com.example.recordily_client.pages.artist

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.SongViewModel
import com.example.recordily_client.view_models.UnreleasedAlbumViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun UnreleasedAlbumPage(navController: NavController, album_id: String){
    val loginViewModel: LoginViewModel = viewModel()
    val unreleasedAlbumViewModel: UnreleasedAlbumViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    unreleasedAlbumViewModel.getAlbumInfo(token, album_id)
    unreleasedAlbumViewModel.getAlbumSongs(token, album_id)

    val album by unreleasedAlbumViewModel.albumInfoResultLiveData.observeAsState()
    val songs by unreleasedAlbumViewModel.albumSongsResultLiveData.observeAsState()

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

            album?.let {
                AlbumHeader(it)

                HorizontalLine()

                UnreleasedAlbumContent(navController, songs, unreleasedAlbumViewModel, token)
            }

        }
    }
}

@Composable
private fun UnreleasedAlbumContent(
    navController: NavController,
    songs: List<SongResponse>?,
    unreleasedAlbumViewModel: UnreleasedAlbumViewModel,
    token: String
){
    val coroutinesScope = rememberCoroutineScope()
    val songViewModel: SongViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        if(songs == null || songs.isEmpty()){
            EmptyState(message = stringResource(id = R.string.no_songs_found))
        }
        else {
            for (song in songs) {
                UnreleasedAlbumSongCard(
                    song = song,
                    onSongClick = {
                        songViewModel.clearQueue()
                        for(queueSong in songs){
                            songViewModel.updateQueue(queueSong.id)
                        }

                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route,
                            popUpTo = Screen.UnreleasedAlbumsPage.route
                        )
                    },
                    onDeleteClick = {
                        coroutinesScope.launch {
                            unreleasedAlbumViewModel.deleteFromAlbum(token, song.id)
                        }
                    }
                )
            }
        }
    }
}
