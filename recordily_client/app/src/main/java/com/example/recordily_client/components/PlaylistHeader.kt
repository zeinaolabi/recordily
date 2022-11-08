package com.example.recordily_client.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.PlaylistResponse

@Composable
fun PlaylistHeader(navController: NavController, playlist: PlaylistResponse){
    Row(
        modifier = Modifier.padding(
            vertical = dimensionResource(id = R.dimen.padding_large),
            horizontal = dimensionResource(id = R.dimen.padding_medium)
        )
    ){
        Image(
            painter =
            if(playlist.picture != ""){
                rememberAsyncImagePainter(playlist.picture)
            }
            else{
                painterResource(id = R.drawable.recordily_dark_logo)
            },
            contentDescription = "playlist picture",
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colors.secondary, CircleShape),
            contentScale = ContentScale.FillBounds
        )

        PlaylistHeaderContent(navController, playlist)
    }
}

@Composable
private fun PlaylistHeaderContent(navController: NavController, playlist: PlaylistResponse){
    Column(
        modifier = Modifier
            .height(125.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Text(
            text = playlist.name,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )


        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            MediumRoundButton(
                text = "Edit Playlist",
                onClick = {
                    navigateTo(
                        navController = navController,
                        destination = Screen.EditPlaylistPage.route + '/' + playlist.id,
                        popUpTo = Screen.PlaylistPage.route
                    )
                }
            )
        }
    }
}