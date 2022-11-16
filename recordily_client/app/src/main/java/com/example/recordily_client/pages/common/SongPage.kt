package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.SongViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.os.CountDownTimer




private val isPlaying = mutableStateOf(false)
private val isPaused = mutableStateOf(false)
private val currentTime = mutableStateOf(0L)
private val popUpVisibility = mutableStateOf(false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SongPage(navController: NavController, songID: String) {
    val songViewModel: SongViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    songViewModel.isLiked(token, songID.toInt())
    songViewModel.getSong(token, songID)

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
                    SongDetailsBox(navController, song, songViewModel, token, songID)
                }
            }

            AnimatedVisibility(
                visible = popUpVisibility.value,
                enter = expandVertically(expandFrom = Alignment.CenterVertically),
                exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
            ) {
                PlaylistPopup(
                    songID = 6,
                    popUpVisibility = popUpVisibility
                )
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            isPlaying.value = false
            isPaused.value = false
            currentTime.value = 0L
            popUpVisibility.value = false
            songViewModel.stopSong()
        }
    }
}

@Composable
private fun SongDetailsBox(navController: NavController, song: SongResponse, songViewModel: SongViewModel, token: String, songID: String){
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
            SongDetails(navController, song, songViewModel, token, songID)
        }
    }
}

//Icons by: icons by https://icons8.com
@Composable
private fun SongDetails(navController: NavController, song: SongResponse, songViewModel: SongViewModel, token: String, songID: String){
    val durationString = songViewModel.getDurationAsString(song.path)
    val duration = songViewModel.getDuration(song.path)
    val progress = remember { mutableStateOf(0f) }
    val finishedSeconds = remember { mutableStateOf(0L) }

    LaunchedEffect(key1 = currentTime.value, key2 = isPlaying.value, key3 = isPaused.value) {
        val timer = object : CountDownTimer(duration!!, 100) {
            override fun onTick(millisUntilFinished: Long) {
                finishedSeconds.value = duration!! - millisUntilFinished
                val total = finishedSeconds.value / duration.toFloat()
                progress.value = total

                if(isPaused.value) {
                    this.cancel()
                }
            }

            override fun onFinish() {
                this.cancel()
                this.start()
            }
        }

        if (isPlaying.value && !isPaused.value) {
            timer.start()
        }
    }

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
        progress = progress.value,
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
            text = durationString ?: "00:00",
            fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }

    PlayButtonRow(navController, song, songViewModel, token, songID)

}

@Composable
private fun PlayButtonRow(navController: NavController, song: SongResponse, songViewModel: SongViewModel, token: String, songID: String){
    val coroutinesScope = rememberCoroutineScope()

    val isLiked by songViewModel.isLikedResultLiveData.observeAsState()
    val queue = songViewModel.getQueue()

    val before =
        if(queue.indexOf(songID.toInt()) == 0) {
            queue[queue.size - 1]
        } else {
            queue[queue.indexOf(songID.toInt()) - 1]
        }

    val after =
        if(queue.indexOf(songID.toInt()) == queue.size - 1) {
            queue[0]
        } else {
            queue[queue.indexOf(songID.toInt()) + 1]
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter =
            if( isLiked == true )
                painterResource(id = R.drawable.red_heart)
            else
                painterResource(id = R.drawable.colored_heart),
            contentDescription = "like",
            modifier = Modifier
                .size(25.dp)
                .bounceClick()
                .clickable(
                    interactionSource = remember { NoRippleInteractionSource() },
                    indication = null
                ) {
                    coroutinesScope.launch {
                        if (isLiked == true) {
                            songViewModel.unlikeSong(token, songID.toInt())
                        } else {
                            songViewModel.likeSong(token, songID.toInt())
                        }
                        songViewModel.isLiked(token, songID.toInt())
                    }
                },
            tint = Color.Unspecified
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "before",
                modifier = Modifier
                    .size(25.dp)
                    .clickable(
                        interactionSource = remember { NoRippleInteractionSource() },
                        indication = null
                    ) {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route + '/' + before,
                            popUpTo = Screen.LandingPage.route
                        )
                    }
                ,
                tint = Color.Unspecified
            )

            if(!isPaused.value && isPlaying.value){
                Icon(
                    painter = painterResource(id = R.drawable.pause_button),
                    contentDescription = "pause",
                    modifier = Modifier
                        .size(90.dp)
                        .bounceClick()
                        .clickable(
                            interactionSource = remember { NoRippleInteractionSource() },
                            indication = null
                        ) {
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
                        .clickable(
                            interactionSource = remember { NoRippleInteractionSource() },
                            indication = null
                        ) {
                            isPlaying.value = true
                            if (isPaused.value) {
                                songViewModel.resumeSong()
                                isPaused.value = false
                            } else {
                                songViewModel.startPlayingSong(song.path)

                                coroutinesScope.launch {
                                    songViewModel.playSong(token, songID.toInt())
                                }
                            }
                        },
                    tint = Color.Unspecified
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "after",
                modifier = Modifier
                    .size(25.dp)
                    .clickable(
                        interactionSource = remember { NoRippleInteractionSource() },
                        indication = null
                    ) {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route + '/' + after,
                            popUpTo = Screen.LandingPage.route
                        )
                    },
                tint = Color.Unspecified
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.add_to_playlist),
            contentDescription = "add to playlist",
            modifier = Modifier
                .size(25.dp)
                .bounceClick()
                .clickable(
                    interactionSource = remember { NoRippleInteractionSource() },
                    indication = null
                ) {
                    popUpVisibility.value = true
                },
            tint = Color.Unspecified,
        )
    }
}

