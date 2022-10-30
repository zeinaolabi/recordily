package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R

@Composable
fun SongsBox(title: String){
    Column(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
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
            for (i in 1..5) {
                SongSquareCard()
            }
        }
    }
}

@Composable
fun SongSquareCard(){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))

    ){
        Image(
            painter = painterResource(R.drawable.recordily_dark_logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(115.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Song title",
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            color = MaterialTheme.colors.onPrimary
        )

        Text(
            text = "Artist name",
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
            color = MaterialTheme.colors.onPrimary
        )
    }
}