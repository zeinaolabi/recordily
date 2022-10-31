package com.example.recordily_client.pages.artist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import com.example.recordily_client.navigation.navigateTo

private val searchInput = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun SongsStatsPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        SongsStatsContent(navController)
    }

}

@ExperimentalAnimationApi
@Composable
private fun SongsStatsContent(navController: NavController){
    val pageOptions = listOf(
        TopNavItem.HomePage, TopNavItem.ViewsStatsPage, TopNavItem.SongsStatsPage
    )
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Header(navController)

        TopNavBar(
            pageOptions = pageOptions,
            currentPage = R.string.songs,
            navController = navController)

        SearchResult(navController)

        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.Bottom
        ){
            BottomNavigationBar(navController)
        }
    }

    AnimatedVisibility(
        visible = popUpVisibility.value,
        enter = expandVertically(expandFrom = Alignment.CenterVertically),
        exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Popup(
            popUpVisibility = popUpVisibility,
            isPlaylist = false
        )
    }
}

@Composable
private fun SearchResult(navController: NavController){
    Column(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        SearchTextField(searchInput)

        Column(modifier = Modifier.verticalScroll(ScrollState(0))){
            for(i in 1..3){
                SongCard(
                    onMoreClick = {
                        popUpVisibility.value = true
                    },
                    onSongClick = {
                        navigateTo(
                            navController = navController,
                            destination = Screen.SongStatsPage.route,
                            popUpTo = Screen.SongsStatsPage.route
                        )
                    }
                )
            }
        }
    }
}