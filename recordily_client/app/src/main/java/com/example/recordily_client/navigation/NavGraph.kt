package com.example.recordily_client.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recordily_client.pages.artist.*
import com.example.recordily_client.pages.common.*

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.LandingPage.route
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
            route = Screen.LandingPage.route
        ){
            CommonLandingPage(navController)
        }

        composable(
            route = Screen.ProfilePage.route
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
            route = Screen.SearchPage.route
        ){
            CommonSearchPage(navController)
        }

        composable(
            route = Screen.LiveEventsPage.route
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

        composable(
            route = Screen.PlaylistPage.route
        ){
            PlaylistPage(navController)
        }

        composable(
            route = Screen.ArtistProfilePage.route
        ){
            ArtistProfilePage(navController)
        }

        composable(
            route = Screen.UploadSongPage.route
        ){
            UploadSongPage(navController)
        }

        composable(
            route = Screen.UploadAlbumPage.route
        ){
            UploadAlbumPage(navController)
        }

        composable(
            route = Screen.EditProfilePage.route
        ){
            EditProfilePage(navController)
        }

        composable(
            route = Screen.SongPage.route
        ){
            SongPage(navController)
        }

        composable(
            route = Screen.SongStatsPage.route
        ){
            SongStatsPage(navController)
        }

        composable(
            route = Screen.SuggestedSongsPage.route
        ){
            SuggestedSongsPage(navController)
        }

        composable(
            route = Screen.CreatePlaylistPage.route
        ){
            CreatePlaylistPage(navController)
        }

        composable(
            route = Screen.AlbumsPage.route
        ){
            AlbumsPage(navController)
        }
    }
}