package com.example.recordily_client.pages.artist

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.Header
import com.example.recordily_client.components.TopNavBar
import com.example.recordily_client.navigation.Destination
import com.example.recordily_client.navigation.Screen
import com.jaikeerthick.composable_graphs.color.BarGraphColors
import com.jaikeerthick.composable_graphs.color.PointHighlight2
import com.jaikeerthick.composable_graphs.composables.BarGraph
import com.jaikeerthick.composable_graphs.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.style.BarGraphVisibility

@Composable
fun ViewsStatsPage(navController: NavController){
    val home = Destination(stringResource(R.string.home), Screen.CommonLandingPage.route)
    val viewStats = Destination(stringResource(R.string.view_stats), Screen.ViewsStatsPage.route)
    val songStats = Destination(stringResource(R.string.song_stats), Screen.SongsStatsPage.route)
    val pageOptions = listOf(
        home, viewStats, songStats
    )

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = "Views",
                    navController = navController
                )

                ViewsContent()
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun ViewsContent(){
    val graphStyle = BarGraphStyle(
        visibility = BarGraphVisibility(
            isYAxisLabelVisible = true
        ),
        colors = BarGraphColors(
            clickHighlightColor = MaterialTheme.colors.primary,
            fillGradient = Brush.verticalGradient(
                listOf(colorResource(id = R.color.primary_lighter), MaterialTheme.colors.primaryVariant, MaterialTheme.colors.primary, colorResource(id = R.color.primary_darker))
            )
        ),
        paddingValues = PaddingValues(dimensionResource(id = R.dimen.padding_medium))
    )

    Column(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
            .verticalScroll(ScrollState(0))
    ){
        BarGraph(
            dataList = listOf(20, 30, 10, 60, 35),
            style = graphStyle
        )

        BarGraph(
            dataList = listOf(20, 30, 10, 60, 35),
            style = graphStyle
        )
    }
}