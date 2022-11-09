package com.example.recordily_client.pages.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.ExitBar
import com.example.recordily_client.components.HorizontalLine
import com.example.recordily_client.components.Popup
import com.example.recordily_client.components.SongCard
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.RecentlyPlaySongsViewModel
import com.example.recordily_client.view_models.TopSongsViewModel

private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun RecentlyPlayedSongsPage(navController: NavController){
    val limit = 40
    val loginViewModel: LoginViewModel = viewModel()
    val recentlyPlayedViewModel: RecentlyPlaySongsViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    recentlyPlayedViewModel.getRecentlyPlayed(token, limit)

    val songs by recentlyPlayedViewModel.recentlyPlayResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()){
            ExitBar(
                navController = navController,
                title = stringResource(id = R.string.recently_played)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.padding_medium)),
            ){
                HorizontalLine()

                RecentlyPlayedSongsContent(navController, songs)
            }
        }

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            Popup(
                popUpVisibility = popUpVisibility,
                isPlaylist = false
            )
        }
    }
}

@Composable
private fun RecentlyPlayedSongsContent(navController: NavController, songs: List<SongResponse>?){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
    ){
        if(songs == null || songs.isEmpty()){
            Column(
                modifier = Modifier
                    .width(330.dp)
                    .fillMaxSize(),
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
                    text = "No songs found",
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        } else {
            for (song in songs) {
                SongCard(
                    song = song,
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongPage.route,
                            popUpTo = Screen.SuggestedSongsPage.route
                        )
                    },
                    onMoreClick = { popUpVisibility.value = true }
                )
            }
        }
    }
}