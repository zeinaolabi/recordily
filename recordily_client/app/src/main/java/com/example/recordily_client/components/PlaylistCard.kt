package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen

@Composable
fun PlaylistCard(navController: NavController){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(60.dp)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .clickable{
                navController.navigate(Screen.PlaylistPage.route) {

                    popUpTo(Screen.PlaylistsPage.route) {
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = true
                }
            }
        ,
        verticalAlignment = Alignment.CenterVertically
    ){
        PlaylistCardContent()
    }
}

@Composable
fun PlaylistCardContent(){
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
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}