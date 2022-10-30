package com.example.recordily_client.pages.artist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen

@Composable
fun UploadSongPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ){

            }
        }
    )
}
