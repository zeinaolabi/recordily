package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun LiveEventCard(eventName: String, picture: String?, name: String, onClick: () -> (Unit)){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(90.dp)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        EventCardContent(eventName, picture, name)

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.Bottom
        ){
            SmallRoundButton(text = "Join Event", onClick = {
                onClick()
            })
        }
    }
}

@Composable
private fun EventCardContent(eventName: String, picture: String?, name: String){
    Row{
        Image(
            painter =
            if(picture !== null || picture != ""){
                rememberAsyncImagePainter(picture)
            }
            else{
                painterResource(id = R.drawable.recordily_dark_logo)
            },
            contentDescription = "logo",
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ){
            Text(
                text = eventName,
                fontWeight = FontWeight.SemiBold,
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp
            )

            Text(
                text = "Hosted by: $name",
                fontWeight = FontWeight.Medium,
                fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp
            )
        }
    }
}