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
import androidx.navigation.NavController
import com.example.recordily_client.R

@Composable
fun ArtistPageHeader(navController: NavController){
    Row(
        modifier = Modifier.padding(
            vertical = dimensionResource(id = R.dimen.padding_large),
            horizontal = dimensionResource(id = R.dimen.padding_medium)
        )
    ){
        Image(
            painter = painterResource(id = R.drawable.recordily_dark_logo),
            contentDescription = "playlist picture",
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colors.secondary, CircleShape),
            contentScale = ContentScale.Crop
        )

        ArtistHeaderContent(navController)
    }
}

@Composable
fun ArtistHeaderContent(navController: NavController){
    Column(
        modifier = Modifier
            .height(110.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Column{
            Text(
                text = "Artist Name",
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small)),
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = "10K Follwers",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            MediumRoundButton(text = "Follow", onClick = {})
        }
    }
}