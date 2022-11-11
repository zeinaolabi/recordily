package com.example.recordily_client.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recordily_client.pages.artist.*
import com.example.recordily_client.pages.common.*
import com.example.recordily_client.view_models.LoginViewModel

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(navController: NavHostController){
    val loginViewModel: LoginViewModel = viewModel()
    val destination = if(loginViewModel.sharedPreferences.getString("token", "") != ""){
        Screen.LandingPage.route
    }
    else{
        Screen.LoginPage.route
    }

    NavHost(
        navController = navController,
        startDestination = destination
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
            route = Screen.PlaylistPage.route + "/{playlist_id}",
            arguments = listOf(navArgument("playlist_id") { type = NavType.StringType })
        ){  backStackEntry ->
            backStackEntry.arguments?.getString("playlist_id")?.let { PlaylistPage(navController, it) }
        }

        composable(
            route = Screen.ArtistProfilePage.route + "/{artist_id}",
            arguments = listOf(navArgument("artist_id") { type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("artist_id")?.let { ArtistProfilePage(navController, it) }
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
            route = Screen.SongStatsPage.route + "/{song_id}"
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("song_id")?.let { SongStatsPage(navController, it) }
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
            route = Screen.AlbumsPage.route + "/{artist_id}",
            arguments = listOf(navArgument("artist_id") { type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("artist_id")?.let { AlbumsPage(navController, it) }
        }

        composable(
            route = Screen.AlbumPage.route + "/{album_id}",
            arguments = listOf(navArgument("album_id") { type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("album_id")?.let { AlbumPage(navController, it) }
        }

        composable(
            route = Screen.ArtistSongsPage.route + "/{artist_id}",
            arguments = listOf(navArgument("artist_id") { type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("artist_id")?.let { ArtistSongsPage(navController, it) }
        }

        composable(
            route = Screen.TopSongsPage.route
        ){
            TopSongsPage(navController)
        }

        composable(
            route = Screen.RecentlyPlayedSongsPage.route
        ){
            RecentlyPlayedSongsPage(navController)
        }

        composable(
            route = Screen.UnreleasedSongsPage.route
        ){
            UnreleasedSongsPage(navController)
        }

        composable(
            route = Screen.UnreleasedAlbumsPage.route
        ){
            UnreleasedAlbumsPage(navController)
        }

        composable(
            route = Screen.UnreleasedAlbumPage.route+ "/{album_id}",
            arguments = listOf(navArgument("album_id") { type = NavType.StringType })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("album_id")?.let { UnreleasedAlbumPage(navController, it) }
        }

        composable(
            route = Screen.ResetPasswordPage.route
        ){
            ResetPasswordPage(navController)
        }

        composable(
            route = Screen.EditPlaylistPage.route + "/{playlist_id}",
            arguments = listOf(navArgument("playlist_id") { type = NavType.StringType })
        ){  backStackEntry ->
            backStackEntry.arguments?.getString("playlist_id")?.let { EditPlaylistPage(navController, it) }
        }

        composable(
            route = Screen.LiveEventPage.route + "/{live_event_id}",
            arguments = listOf(navArgument("live_event_id") { type = NavType.StringType })
        ){  backStackEntry ->
            backStackEntry.arguments?.getString("live_event_id")?.let { LiveEventPage(navController, it) }
        }
    }
}