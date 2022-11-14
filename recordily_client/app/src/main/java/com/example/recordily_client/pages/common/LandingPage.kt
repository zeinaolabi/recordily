package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommonLandingPage(navController: NavController){
    val pageOptions = listOf(
        TopNavItem.HomePage, TopNavItem.ViewsStatsPage, TopNavItem.SongsStatsPage
    )
    val loginViewModel : LoginViewModel = viewModel()
    val landingPageViewModel : LandingPageViewModel = viewModel()

    val limit = 5
    val artistType = 0
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    val userType = loginViewModel.sharedPreferences.getInt("user_type_id", -1)

    landingPageViewModel.getTopPlayed(token, limit)
    landingPageViewModel.getRecentlyPlayed(token, limit)
    landingPageViewModel.getSuggested(token, limit)
    landingPageViewModel.getTopLiked(token, limit)

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            ){
                if(userType == artistType){
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
private fun LandingPageContent(navController: NavController, landingPageViewModel : LandingPageViewModel = viewModel()){

    val getTopPlayedResponse by landingPageViewModel.topPlayedResultLiveData.observeAsState()
    val getRecentlyPlayedResponse by landingPageViewModel.recentlyPlayedResultLiveData.observeAsState()
    val getSuggestedResponse by landingPageViewModel.suggestedResultLiveData.observeAsState()
    val getTopLikedResponse by landingPageViewModel.topLikedResultLiveData.observeAsState()

    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
    ){
        SongsBox(
            title = stringResource(id = R.string.suggested),
            navController = navController,
            songs = getSuggestedResponse
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_songs),
            navController = navController,
            songs = getTopPlayedResponse
        )

        ArtistsBox(
            title = stringResource(id = R.string.top_5_artists),
            navController = navController
        )

        SongsBox(
            title = stringResource(id = R.string.top_5_liked_songs),
            navController = navController,
            songs = getTopLikedResponse
        )

        SongsBox(
            title = stringResource(id = R.string.recently_played),
            navController = navController,
            songs = getRecentlyPlayedResponse
        )
    }
}