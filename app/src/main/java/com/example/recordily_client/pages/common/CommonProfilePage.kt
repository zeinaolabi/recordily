package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.ProfileHeader
import com.example.recordily_client.components.TopNavBar

@Composable
fun CommonProfilePage(navController: NavController){
    val pageOptions = listOf(
        stringResource(R.string.profile), stringResource(R.string.unreleased)
    )

    Scaffold(
        topBar = { ProfileHeader(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TopNavBar(pageOptions = pageOptions, currentPage = "Profile")

        }
    }
}