package com.example.recordily_client.pages.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.view_models.LandingPageViewModel
import com.example.recordily_client.view_models.LoginViewModel

@Composable
fun CommonLandingPage(navController: NavController){
    val loginViewModel : LoginViewModel = viewModel()
    val pageOptions = listOf(
        TopNavItem.HomePage, TopNavItem.ViewsStatsPage, TopNavItem.SongsStatsPage
    )
    val limit = 5
    val landingPageViewModel : LandingPageViewModel = viewModel()

    landingPageViewModel.getTopPlayed(limit)
    landingPageViewModel.getRecentlyPlayed(limit)
    landingPageViewModel.getSuggested(limit)
    landingPageViewModel.getTopLiked(limit)

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(loginViewModel.sharedPreferences.getInt("user_type_id", -1) == 0){
                    TopNavBar(
                        pageOptions = pageOptions,
                        currentPage = R.string.home,
                        navController = navController
                    )
                }

                LandingPageContent(navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun LandingPageContent(navController: NavController){

    val landingPageViewModel : LandingPageViewModel = viewModel()

    val getTopPlayedResponse = landingPageViewModel.topPlayedResultLiveData.observeAsState()
    val getRecentlyPlayedResponse = landingPageViewModel.recentlyPlayedResultLiveData.observeAsState()
    val getSuggestedResponse = landingPageViewModel.suggestedResultLiveData.observeAsState()
    val getTopLikedResponse = landingPageViewModel.topLikedResultLiveData.observeAsState()

    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
    ){
        SongsBox(
            title = stringResource(id = R.string.suggested),
            navController = navController,
            data = getSuggestedResponse.value
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_songs),
            navController = navController,
            data = getTopPlayedResponse.value
        )

        ArtistsBox(
            title = stringResource(id = R.string.top_5_artists),
            navController = navController
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_liked_songs),
            navController = navController,
            data = getTopLikedResponse.value
        )

        SongsBox(
            title = stringResource(id = R.string.recently_played),
            navController = navController,
            data = getRecentlyPlayedResponse.value
        )
    }
}