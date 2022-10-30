package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*


@Composable
fun PlaylistPage(navController: NavController){
    Scaffold(
        topBar = { PlaylistHeader(navController) },
        content = {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                        .drawBehind {
                            val strokeWidth = Stroke.DefaultMiter * 1.5f
                            val y = size.height - strokeWidth / 10 + 20

                            drawLine(
                                Color.Black,
                                Offset(1f, y),
                                Offset(size.width, y),
                                strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }
                ){}

                PlaylistPageContent(navController)
            }
        }
    )
}

@Composable
fun PlaylistPageContent(navController: NavController){
    Column(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ){
        for(i in 1..3){
            SongCard(navController)
        }
    }
}