package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.Header
import com.example.recordily_client.components.SongsBox
import com.example.recordily_client.components.TopNavBar

@Composable
fun CommonLandingPage(navController: NavController){

    Scaffold(
        topBar = { Header(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopNavBar(currentPage = "Home")

            SongsBox("Suggested Songs")
        }
    }

}