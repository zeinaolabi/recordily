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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.recordily_client.R

@Composable
fun Popup(popUpVisibility: MutableState<Boolean>, isPlaylist: Boolean){
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
                PlaylistPopupContent()

            }
            else{
                RegularPopupContent()
            }
        }
    }
}

@Composable
private fun RegularPopupContent(){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
    ){
        AddToLikes()

        AddToPlaylist()
    }
}

@Composable
private fun PlaylistPopupContent(){
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
    ){

        AddToLikes()

        AddToPlaylist()

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
private fun AddToPlaylist(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
private fun AddToLikes(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = "delete",
            modifier = Modifier.size(20.dp),
            tint = Color.White
        )

        Text(
            text="Like",
            color = Color.White
        )
    }
}