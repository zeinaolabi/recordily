package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.responses.PlaylistResponse

@Composable
fun PlaylistsCard(title: String, playlists: List<PlaylistResponse>?, destination: ()->(Unit), onPlaylistClick: ()->(Unit)){
    Column(
        modifier = Modifier.padding(bottom= dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )

        PlaylistsCardContent(playlists, destination, onPlaylistClick)
    }
}

@Composable
private fun PlaylistsCardContent(playlists: List<PlaylistResponse>?,destination: ()->(Unit), onPlaylistClick: ()->(Unit)){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (playlists != null) {
            for(playlist in playlists){
                PlaylistCard(
                    playlist = playlist,
                    onPlaylistClick = {
                        onPlaylistClick()
                    }
                )
            }
        }

        SmallTealButton(
            text = stringResource(id = R.string.more),
            onClick = {
                destination()
            }
        )

    }
}

@Composable
fun PlaylistCard(playlist: PlaylistResponse, onPlaylistClick: () -> (Unit)){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(65.dp)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .clickable(
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ){
                onPlaylistClick()
            }
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        PlaylistCardContent(playlist)
    }
}

@Composable
private fun PlaylistCardContent(playlist: PlaylistResponse){
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Image(
            painter =
            if(playlist.picture != ""){
                rememberAsyncImagePainter(playlist.picture)
            }
            else{
                painterResource(id = R.drawable.recordily_dark_logo)
            },
            contentDescription = "logo",
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = playlist.name,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )
    }
}