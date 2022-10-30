package com.example.recordily_client.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recordily_client.pages.artist.RecordPage
import com.example.recordily_client.pages.artist.SongsStatsPage
import com.example.recordily_client.pages.artist.UnreleasedPage
import com.example.recordily_client.pages.artist.ViewsStatsPage
import com.example.recordily_client.pages.common.*

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.CommonLandingPage.route
    ){
        composable(
            route = Screen.LoginPage.route,
//            exitTransition = {_,_ ->
//                fadeOut(animationSpec = tween(300))
//            }
        ){
            LoginPage(navController)
        }

        composable(
            route = Screen.RegistrationPage.route
        ){
            RegistrationPage(navController)
        }

        composable(
            route = Screen.CommonLandingPage.route
        ){
            CommonLandingPage(navController)
        }

        composable(
            route = Screen.CommonProfilePage.route
        ){
            CommonProfilePage(navController)
        }

        composable(
            route = Screen.UnreleasedPage.route
        ){
            UnreleasedPage(navController)
        }

        composable(
            route = Screen.RecordPage.route
        ){
            RecordPage(navController)
        }

        composable(
            route = Screen.ViewsStatsPage.route
        ){
            ViewsStatsPage(navController)
        }

        composable(
            route = Screen.SongsStatsPage.route
        ){
            SongsStatsPage(navController)
        }

        composable(
            route = Screen.CommonSearchPage.route
        ){
            CommonSearchPage(navController)
        }

        composable(
            route = Screen.CommonLiveEventsPage.route
        ){
            CommonLiveEventsPage(navController)
        }

        composable(
            route = Screen.LibraryPage.route
        ){
            LibraryPage(navController)
        }

        composable(
            route = Screen.PlaylistsPage.route
        ){
            PlaylistsPage(navController)
        }

        composable(
            route = Screen.ArtistsPage.route
        ){
            ArtistsPage(navController)
        }
    }
}