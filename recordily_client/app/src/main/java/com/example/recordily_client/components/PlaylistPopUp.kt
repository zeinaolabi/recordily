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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R

@Composable
fun PlaylistPopup(songID: Int, popUpVisibility: MutableState<Boolean>){
//    val loginViewModel: LoginViewModel = viewModel()
//    val popUpViewModel: PopUpViewModel = viewModel()
//    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
//
//    popUpViewModel.isLiked(token, songID)

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
            PopupContent()
        }
    }
}

@Composable
private fun PopupContent(){
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
        for(i in 1..8) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {}
            ) {
                Image(
                    painter = painterResource(R.drawable.recordily_dark_logo),
                    contentDescription = "album picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "Playlist name",
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}