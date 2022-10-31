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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*

@Composable
fun SongPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.playing)) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_large)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Image(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "song picture",
                    modifier = Modifier
                        .size(330.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds
                )

                SongDetailsBox()
            }
        }
    )
}

@Composable
private fun SongDetailsBox(){
    Surface(
        color = Color.Black.copy(alpha = 0.65f),
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clip(MaterialTheme.shapes.medium)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
        ){
            SongDetails()
        }
    }
}

//Icons by: icons by https://icons8.com
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
        progress = 0.7f,
        modifier = Modifier
            .fillMaxWidth()

    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.End
    ){
        Text(
            text = "2:55",
            fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }

    PlayButtonRow()

}

@Composable
private fun PlayButtonRow(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.colored_heart),
            contentDescription = "like",
            modifier = Modifier
                .size(25.dp)
                .bounceClick(),
            tint = Color.Unspecified
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ){
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "before",
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )

            Icon(
                painter = painterResource(id = R.drawable.play_button),
                contentDescription = "play",
                modifier = Modifier
                    .size(90.dp)
                    .bounceClick(),
                tint = Color.Unspecified
            )

            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "after",
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.add_to_playlist),
            contentDescription = "add to playlist",
            modifier = Modifier
                .size(25.dp)
                .bounceClick(),
            tint = Color.Unspecified
        )
    }
}

