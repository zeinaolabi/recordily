package com.example.recordily_client.navigation

import androidx.navigation.NavController

fun navigateTo(navController: NavController, destination: String, popUpTo: String){
    navController.navigate(destination) {

        popUpTo(popUpTo)

        launchSingleTop = true
        restoreState = true
    }
}