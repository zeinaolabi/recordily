package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.PopUpViewModel

@Composable
fun PlaylistPopup(songID: Int, popUpVisibility: MutableState<Boolean>){
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
                .height(300.dp)
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
            PopupContent(songID)
        }
    }
}

@Composable
private fun PopupContent(songID: Int){
    val loginViewModel: LoginViewModel = viewModel()
    val popUpViewModel: PopUpViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    popUpViewModel.getPlaylists(token)

    val playlists = popUpViewModel.playlistsResultLiveData.observeAsState()

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
        if(playlists.value === null || playlists.value!!.isEmpty()){
            EmptyState(message = stringResource(id = R.string.no_playlists_found))
        }
        else{
            for(playlist in playlists.value!!) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {}
                ) {
                    Image(
                        painter =
                        if(playlist.picture !== null || playlist.picture != ""){
                            rememberAsyncImagePainter(playlist.picture)
                        }
                        else{
                            painterResource(id = R.drawable.recordily_dark_logo)
                        },
                        contentDescription = "album picture",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = playlist.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}