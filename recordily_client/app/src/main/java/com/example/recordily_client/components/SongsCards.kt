package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R

@Composable
fun SongsCards(title: String, navController: NavController, destination: String, onClick: () -> (Unit)){
    Column(
        modifier = Modifier.padding(bottom= dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )

        CardsContent(navController, destination, onClick)
    }
}

@Composable
fun CardsContent(navController: NavController, destination: String, onClick: () -> (Unit)){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .verticalScroll(ScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        for(i in 1..3){
            SongCard(navController, onClick = {})
        }

        SmallTealButton(stringResource(id = R.string.more), onClick = {
            navController.navigate(destination)
        })
        
    }
}

@Composable
fun SongCard(navController: NavController, onClick: () -> (Unit)){
    Row(
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            .height(60.dp)
            .fillMaxWidth()
            .shadow(5.dp)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically
    ){
        SongCardContent(onClick)
    }
}

@Composable
fun SongCardContent(onClick: () -> (Unit)){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        SongDetails()

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .clickable{
                    onClick()
                },
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "more",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun SongDetails(){
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

        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Text(
                text = "Song title",
                fontWeight = FontWeight.Bold,
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
}