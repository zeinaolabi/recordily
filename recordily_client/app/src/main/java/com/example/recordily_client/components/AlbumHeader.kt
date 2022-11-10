package com.example.recordily_client.components

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
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.responses.AlbumResponse

@Composable
fun AlbumHeader(album: AlbumResponse){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(id = R.dimen.padding_medium)
            )
    ){
        Image(
            painter =
            if(album.picture !== null || album.picture != ""){
                rememberAsyncImagePainter(album.picture)
            }
            else{
                painterResource(id = R.drawable.recordily_dark_logo)
            },
            contentDescription = "album picture",
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colors.secondary, CircleShape),
            contentScale = ContentScale.Crop
        )

        AlbumHeaderContent(album)
    }
}

@Composable
private fun AlbumHeaderContent(album: AlbumResponse){
    Column(
        modifier = Modifier
            .height(125.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Column{
            Text(
                text = album.name,
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = album.artist_name,
                fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
        }

    }
}