package com.example.recordily_client.pages.artist

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
import androidx.core.app.ActivityCompat

import androidx.core.app.ActivityCompat.startActivityForResult

private val songName = mutableStateOf("")

@Composable
fun UploadAlbumPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
                ){
                    UploadSongContent()
                }
            }
        }
    )
}

@Composable
fun UploadAlbumContent(){
    val logo = if (isSystemInDarkTheme()) R.drawable.recordily_gray_logo else R.drawable.recordily_light_mode

    Text(
        text = stringResource(id = R.string.new_song),
        fontSize = dimensionResource(id = R.dimen.font_title).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary
    )

    Image(
        painter = painterResource(id = logo),
        contentDescription = "logo",
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
    )

    TextField(
        input = songName,
        text = stringResource(id = R.string.song_name),
        visibility = true
    )

    MediumRoundButton(text = stringResource(id = R.string.upload), onClick = {})
}

