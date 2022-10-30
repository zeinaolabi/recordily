package com.example.recordily_client.pages.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.SearchTextField
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen

private val searchInput = mutableStateOf("")

@Composable
fun LibraryPage(navController: NavController){

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                LibraryPageContent(navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
fun LibraryPageContent(navController: NavController){
    val likes = Destination(stringResource(R.string.likes), Screen.CommonLandingPage.route)
    val playlists = Destination(stringResource(R.string.playlists), Screen.ViewsStatsPage.route)
    val artists = Destination(stringResource(R.string.artists), Screen.SongsStatsPage.route)
    val pageOptions = listOf(
        likes, playlists, artists
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        TopNavBar(
            pageOptions = pageOptions,
            currentPage = "Likes",
            navController = navController
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        for(i in 1..3){
            SongCard(navController)
        }
    }


}