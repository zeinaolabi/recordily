package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.responses.ArtistResponse

private val searchInput = mutableStateOf("")

@Composable
fun ArtistsPage(navController: NavController) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                ArtistsPageContent(navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun ArtistsPageContent(navController: NavController){
    val pageOptions = listOf(
        TopNavItem.LikesPage, TopNavItem.PlaylistsPage, TopNavItem.ArtistsPage
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        LibraryHeader(
            input = searchInput,
            navController = navController
        )

        TopNavBar(
            pageOptions = pageOptions,
            currentPage = R.string.artists,
            navController = navController
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        for(i in 1..3){
            ArtistCard(
                data = ArtistResponse("", "", "", 1, "", "", "", 1),
                onClick = {
                navController.navigate(Screen.ArtistProfilePage.route) {
                    popUpTo(Screen.ArtistsPage.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        }
    }
}