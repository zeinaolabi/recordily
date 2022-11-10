package com.example.recordily_client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recordily_client.R
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.PopUpViewModel
import kotlinx.coroutines.launch

@Composable
fun Popup(
    songID: Int,
    popUpVisibility: MutableState<Boolean>,
    playlistPopUpVisibility: MutableState<Boolean>,
    isPlaylist: Boolean
){
    val loginViewModel: LoginViewModel = viewModel()
    val popUpViewModel: PopUpViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    popUpViewModel.isLiked(token, songID)

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
                .height(200.dp)
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
            if(isPlaylist){
                PlaylistPopupContent(
                    popUpViewModel,
                    token,
                    songID,
                    popUpVisibility,
                    playlistPopUpVisibility
                )

            }
            else{
                RegularPopupContent(
                    popUpViewModel,
                    token,
                    songID,
                    popUpVisibility,
                    playlistPopUpVisibility
                )
            }
        }
    }
}

@Composable
private fun RegularPopupContent(
    popUpViewModel: PopUpViewModel,
    token: String, songID:Int,
    popUpVisibility: MutableState<Boolean>,
    playlistPopUpVisibility: MutableState<Boolean>
){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
    ){
        AddToLikes(popUpViewModel, token, songID)

        AddToPlaylist(popUpVisibility, playlistPopUpVisibility)
    }
}

@Composable
private fun PlaylistPopupContent(
    popUpViewModel: PopUpViewModel,
    token: String,
    songID: Int,
    popUpVisibility: MutableState<Boolean>,
    playlistPopUpVisibility: MutableState<Boolean>
){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
    ){

        AddToLikes(popUpViewModel, token, songID)

        AddToPlaylist(popUpVisibility, playlistPopUpVisibility)

        DeleteFromPlaylist()

    }
}

@Composable
private fun DeleteFromPlaylist(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )

        Text(
            text="Delete",
            color = Color.White
        )
    }
}

@Composable
private fun AddToPlaylist(popUpVisibility: MutableState<Boolean>, playlistPopUpVisibility: MutableState<Boolean>){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.clickable {
            popUpVisibility.value = false
            playlistPopUpVisibility.value = true
        }
    ){
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "add to playlist",
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )

        Text(
            text="Add to playlist",
            color = Color.White
        )
    }
}

@Composable
private fun AddToLikes(popUpViewModel: PopUpViewModel, token: String, songID: Int){
    val coroutinesScope = rememberCoroutineScope()
    val isLiked by popUpViewModel.isLikedResultLiveData.observeAsState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .clickable
            {
                coroutinesScope.launch {
                    if(isLiked == true){
                        popUpViewModel.unlikeSong(token, songID)
                    }
                    else{
                        popUpViewModel.likeSong(token, songID)
                    }
                    popUpViewModel.isLiked(token, songID)
                }
            }
    ){
        Icon(
            painter =
            if( isLiked == true )
                painterResource(id = R.drawable.red_heart)
            else
                painterResource(id = R.drawable.heart),
            contentDescription = "delete",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        Text(
            text= if( isLiked == true ) "Unlike" else "Like",
            color = Color.White
        )
    }
}