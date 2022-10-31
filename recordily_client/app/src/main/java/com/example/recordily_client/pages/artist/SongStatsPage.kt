package com.example.recordily_client.pages.artist

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.ExitBar
import com.example.recordily_client.components.HorizontalLine
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility

@Composable
fun SongStatsPage(navController: NavController){

    Scaffold(
        topBar = { ExitBar(
            navController = navController,
            title = stringResource(id = R.string.song_statistics)
        )},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ){
                SongStatsContent()
            }
        }
    )
}

@Composable
private fun SongStatsContent(){
    val graphStyle = LineGraphStyle(
        visibility = LinearGraphVisibility(
            isHeaderVisible = true,
            isYAxisLabelVisible = true,
            isCrossHairVisible = true
        ),
        colors = LinearGraphColors(
            lineColor = MaterialTheme.colors.primaryVariant,
            pointColor = colorResource(id = R.color.primary_darker),
            clickHighlightColor = colorResource(id = R.color.primary_darker),
            fillGradient = Brush.verticalGradient(
                listOf(MaterialTheme.colors.primary, Color.Transparent)
            )
        )
    )

    Text(
        text = "Song name",
        fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onPrimary
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = "Likes: 12K",
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )

        Text(
            text = "Plays: 230K",
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )
    }

    LineGraph(
        xAxisData = listOf("Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat").map {
            GraphData.String(it)
        },
        yAxisData = listOf(200, 40, 60, 450, 700, 30, 50),
        style = graphStyle
    )
}

