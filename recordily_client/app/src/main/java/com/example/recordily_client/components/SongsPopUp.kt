package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.PopUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SongsPopUp(
    input: MutableState<String>,
    popUpVisibility: MutableState<Boolean>,
    liveEventID: String,
    onClick: () -> (Unit)
){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            color = Color.Black.copy(alpha = 0.4f),
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { NoRippleInteractionSource() },
                    indication = null
                ) {
                    popUpVisibility.value = false
                }
        ){}

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                )
                .background(Color.Black)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .clickable(
                    interactionSource = remember { NoRippleInteractionSource() },
                    indication = null
                ) {
                    popUpVisibility.value = true
                },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_large),
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    )
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    SmallTealButton(text = stringResource(id = R.string.end_live)) {
                        onClick()
                    }
                }

                SearchTextField(input = input)

                PopupContent(input, popUpVisibility, liveEventID)
            }
        }
    }
}

@Composable
private fun PopupContent(
    input: MutableState<String>,
    popUpVisibility: MutableState<Boolean>,
    liveEventID: String
){
    val popUpViewModel: PopUpViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    val searchResult by popUpViewModel.searchResultLiveData.observeAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
            .verticalScroll(ScrollState(0))
    ){
        if(input.value !== "") {
            popUpViewModel.getSearchResult(token, input.value)
            SongContent(searchResult, popUpVisibility, liveEventID)
        }
    }
}

@Composable
private fun SongContent(
    songs: List<SongResponse>?,
    popUpVisibility: MutableState<Boolean>,
    liveEventID: String
){
    val coroutineScope = rememberCoroutineScope()
    val popUpViewModel: PopUpViewModel = viewModel()

    if(songs === null || songs.isEmpty()){
        EmptyState(message = stringResource(id = R.string.no_playlists_found))
    }
    else {
        for (song in songs) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable
                    {
                        coroutineScope.launch {
                            popUpViewModel.playSong(song.id.toString(), liveEventID)
                            popUpVisibility.value = false
                        }
                    }
            ) {
                Image(
                    painter =
                    if (song.picture !== null || song.picture != "") {
                        rememberAsyncImagePainter(song.picture)
                    } else {
                        painterResource(id = R.drawable.recordily_dark_logo)
                    },
                    contentDescription = "album picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = song.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}