package com.example.recordily_client.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.pages.common.LoginPage
import com.example.recordily_client.pages.common.RegistrationPage
import com.example.recordily_client.pages.common.CommonLandingPage

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
    }
}