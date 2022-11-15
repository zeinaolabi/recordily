package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.LikesPageViewModel
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.SongViewModel

val isPlaying = mutableStateOf(false)
val isPaused = mutableStateOf(false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SongPage(navController: NavController) {
    val songViewModel: SongViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    songViewModel.getSong(token, "6")

    val song = songViewModel.songResultLiveData.observeAsState().value

    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.playing)) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_large)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Image(
                    painter =
                    if(song?.picture != null && song.picture != ""){
                        rememberAsyncImagePainter(song.picture)
                    }
                    else{
                        painterResource(id = R.drawable.recordily_dark_logo)
                    },
                    contentDescription = "song picture",
                    modifier = Modifier
                        .size(330.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                if (song != null) {
                    SongDetailsBox(song, songViewModel)
                }
            }
        }
    )
}

@Composable
private fun SongDetailsBox(song: SongResponse, songViewModel: SongViewModel){
    Surface(
        color = Color.Black.copy(alpha = 0.65f),
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(MaterialTheme.shapes.medium)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
        ){
            SongDetails(song, songViewModel)
        }
    }
}

//Icons by: icons by https://icons8.com
@Composable
private fun SongDetails(song: SongResponse, songViewModel: SongViewModel){
    val duration = songViewModel.getDuration(song.path)

    Text(
        text = song.name,
        fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    Text(
        text = song.artist_name,
        fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White
    )

    LinearProgressIndicator(
        color = Color.White,
        progress = 0.7f,
        modifier = Modifier
            .fillMaxWidth()

    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.End
    ){
        Text(
            text = duration ?: "00:00",
            fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }

    PlayButtonRow(song, songViewModel)

}

@Composable
private fun PlayButtonRow(song: SongResponse, songViewModel: SongViewModel){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.colored_heart),
            contentDescription = "like",
            modifier = Modifier
                .size(25.dp)
                .bounceClick(),
            tint = Color.Unspecified
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "before",
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )

            if(!isPaused.value && isPlaying.value){
                Icon(
                    painter = painterResource(id = R.drawable.pause_button),
                    contentDescription = "pause",
                    modifier = Modifier
                        .size(90.dp)
                        .bounceClick()
                        .clickable{
                            isPaused.value = true
                            songViewModel.pauseSong()
                        }
                    ,
                    tint = Color.Unspecified
                )
            }
            else{
                Icon(
                    painter = painterResource(id = R.drawable.play_button),
                    contentDescription = "play",
                    modifier = Modifier
                        .size(90.dp)
                        .bounceClick()
                        .clickable{
                            isPlaying.value = true
                            if(isPaused.value){
                                songViewModel.resumeSong()
                                isPaused.value = false
                            } else {
                                songViewModel.playSong(song.path)
                            }
                        }
                    ,
                    tint = Color.Unspecified
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "after",
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.add_to_playlist),
            contentDescription = "add to playlist",
            modifier = Modifier
                .size(25.dp)
                .bounceClick(),
            tint = Color.Unspecified
        )
    }
}

