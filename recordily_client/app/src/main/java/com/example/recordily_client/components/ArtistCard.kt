package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.responses.UserResponse

@Composable
fun ArtistCard(artist: UserResponse, onClick: () -> (Unit)){
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
            ){ onClick() },
        verticalAlignment = Alignment.CenterVertically
    ){
        ArtistCardContent(artist)
    }
}

@Composable
private fun ArtistCardContent(artist: UserResponse){
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Image(
            painter = if(artist.profile_picture != ""){
                rememberAsyncImagePainter(artist.profile_picture)
            }
            else{
                painterResource(id = R.drawable.profile_picture)
            },
            contentDescription = "logo",
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Text(
            text = artist.name,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}