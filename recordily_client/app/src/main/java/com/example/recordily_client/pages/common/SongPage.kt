package com.example.recordily_client.pages.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.TextField
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen
import android.content.Intent
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat

import androidx.core.app.ActivityCompat.startActivityForResult

@Composable
fun SongPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.playing)) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                SongPageContent()
            }
        }
    )
}

@Composable
private fun SongPageContent(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ){
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = "song picture",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

        SongDetailsBox()
    }
}

@Composable
private fun SongDetailsBox(){
    Surface(
        color = Color.Black.copy(alpha = 0.4f),
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(MaterialTheme.shapes.medium)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ){
            SongDetails()
        }
    }
}

@Composable
private fun SongDetails(){
    Text(
        text = "Song name",
        fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    Text(
        text = "Artist name",
        fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White
    )

    LinearProgressIndicator(
        color = Color.White,
        progress = 0.7f
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "2:55",
            fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.End
        )
    }

    PlayButtonRow()

}


