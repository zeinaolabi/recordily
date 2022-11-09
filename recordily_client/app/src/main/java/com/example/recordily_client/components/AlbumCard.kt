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
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.AlbumResponse

@Composable
fun AlbumsCards(title: String, albums: List<AlbumResponse>, navController: NavController, buttonDestination: ()->(Unit)){
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

        AlbumsCardContent(albums, navController,  buttonDestination)
    }
}

@Composable
private fun AlbumsCardContent(albums: List<AlbumResponse>, navController: NavController, destination: ()->(Unit)){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for(album in albums){
            AlbumCard(album, navController)
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
fun AlbumCard(album: AlbumResponse, navController: NavController){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(60.dp)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ){
        AlbumCardContent(album, navController)
    }
}

@Composable
private fun AlbumCardContent(album: AlbumResponse, navController: NavController){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable(
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ) {
                navigateTo(
                    navController = navController,
                    destination = Screen.AlbumPage.route + '/' + album.id,
                    popUpTo = Screen.ArtistProfilePage.route
                )
            }
    )
    {
        Image(
            painter = painterResource(R.drawable.recordily_dark_logo),
            contentDescription = "album picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Text(
                text = album.name,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = album.artist_name,
                fontWeight = FontWeight.Medium,
                fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}