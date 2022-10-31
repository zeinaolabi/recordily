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
import com.example.recordily_client.R

@Composable
fun PlaylistsCard(title: String, destination: ()->(Unit), onPlaylistClick: ()->(Unit)){
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

        PlaylistsCardContent(destination, onPlaylistClick)
    }
}

@Composable
private fun PlaylistsCardContent(destination: ()->(Unit), onPlaylistClick: ()->(Unit)){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for(i in 1..3){
            PlaylistCard(onPlaylistClick)
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
fun PlaylistCard(onPlaylistClick: () -> (Unit)){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(60.dp)
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
        PlaylistCardContent()
    }
}

@Composable
private fun PlaylistCardContent(){
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Image(
            painter = painterResource(R.drawable.recordily_dark_logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Playlist Title",
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )
    }
}