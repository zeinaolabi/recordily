package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen


@Composable
fun Header(navController: NavController){
    val logo = if (isSystemInDarkTheme()) R.drawable.recordily_gray_logo else R.drawable.recordily_light_mode

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(logo),
                contentDescription = "logo",
                modifier = Modifier
                    .height(64.dp)
                    .width(80.dp)
            )

            Text(
                text="Recordily",
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily.Cursive,
                fontSize = dimensionResource(R.dimen.font_large).value.sp
            )
        }

        Image(
            painter = painterResource(logo),
            contentDescription = "profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
                .clickable {
                    navController.navigate(Screen.CommonProfilePage.route)
                }
        )
    }
}