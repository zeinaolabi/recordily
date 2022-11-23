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
import com.example.recordily_client.responses.UserResponse

@Composable
fun ArtistsBox(title: String, navController: NavController, artists: List<UserResponse>?) {
    Column(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
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
        ) {
            if (artists != null) {
                for (artist in artists) {
                    ArtistsBoxContent(
                        onClick = {
                            navigateTo(
                                navController = navController,
                                destination = Screen.ArtistProfilePage.route + '/' + artist.id,
                                popUpTo = Screen.ArtistsPage.route
                            )
                        },
                        artist = artist
                    )
                }
            }
        }
    }
}


@Composable
private fun ArtistsBoxContent(onClick: () -> (Unit), artist: UserResponse){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            .bounceClick()
            .clickable(
                interactionSource = remember { NoRippleInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ){
        Image(
            painter =
            if(artist.profile_picture !== null && artist.profile_picture != ""){
                rememberAsyncImagePainter(artist.profile_picture)
            }
            else{
                painterResource(id = R.drawable.profile_picture)
            },
            contentDescription = "logo",
            modifier = Modifier
                .size(115.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = artist.name ?: "Artist Name",
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            color = MaterialTheme.colors.onPrimary
        )
    }
}