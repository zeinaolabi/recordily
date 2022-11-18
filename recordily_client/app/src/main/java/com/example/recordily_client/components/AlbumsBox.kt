package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.AlbumResponse

@Composable
fun AlbumsBox(title: String, navController: NavController, albums: List<AlbumResponse>?){
    Column(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .horizontalScroll(ScrollState(0)),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (albums != null) {
                for (album in albums) {
                    AlbumsBoxContent(
                        destination = {
                            navigateTo(
                                navController = navController,
                                destination = Screen.AlbumPage.route + '/' + album.id,
                                popUpTo = Screen.ArtistProfilePage.route
                            )
                        },
                        album = album
                    )
                }
            }
        }
    }
}

@Composable
private fun AlbumsBoxContent(destination: () -> (Unit), album: AlbumResponse){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            .bounceClick()
            .clickable(
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ) {
                destination()
            }
    ){
        Image(
            painter =
            if(album.picture !== null && album.picture != ""){
                rememberAsyncImagePainter(album.picture)
            }
            else{
                painterResource(id = R.drawable.recordily_dark_logo)
            },
            contentDescription = "album picture",
            modifier = Modifier
                .size(115.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = album.name,
            fontWeight = FontWeight.SemiBold,
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