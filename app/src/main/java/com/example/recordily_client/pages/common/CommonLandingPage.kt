package com.example.recordily_client.pages.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.Header

@Composable
fun CommonLandingPage(navController: NavController){

    Scaffold(
        topBar = { Header() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {

    }

//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colors.background)
//    ) {
//        Column(){
//            Header()
//
//            BottomNavigationBar(navController = navController )
//
//        }
//    }
}