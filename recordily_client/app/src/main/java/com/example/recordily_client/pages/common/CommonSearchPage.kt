package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.SearchTextField
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.SongsCards
import com.example.recordily_client.navigation.Screen

private val searchInput = mutableStateOf("")

@Composable
fun CommonSearchPage(navController: NavController){

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_large),
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                SearchPageContent(navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
fun SearchPageContent(navController: NavController){
    Text(
        text = stringResource(id = R.string.discover_songs),
        fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary,
        )

    SearchTextField(searchInput)

    SongsCards(
        title = stringResource(id = R.string.suggested),
        navController = navController,
        destination = Screen.CommonLandingPage.route,
        onSongClick = {},
        onMoreClick = {}
    )
}