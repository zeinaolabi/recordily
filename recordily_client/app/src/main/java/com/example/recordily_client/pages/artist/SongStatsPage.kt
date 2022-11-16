package com.example.recordily_client.pages.artist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.ExitBar
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.PlaylistsViewModel
import com.example.recordily_client.view_models.SongStatsViewModel
import com.jaikeerthick.composable_graphs.color.LinearGraphColors
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SongStatsPage(navController: NavController, song_id: String){
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    val songStatsViewModel: SongStatsViewModel = viewModel()

    songStatsViewModel.getSongViewsPerMonth(token, song_id)
    songStatsViewModel.getSongViews(token, song_id)
    songStatsViewModel.getSongLikes(token, song_id)
    songStatsViewModel.getSong(token, song_id)

    val viewsPerMonth by songStatsViewModel.viewsPerMonthResultLiveData.observeAsState()
    val views by songStatsViewModel.viewsResultLiveData.observeAsState()
    val likes by songStatsViewModel.likesResultLiveData.observeAsState()
    val song by songStatsViewModel.songResultLiveData.observeAsState()

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
                if(viewsPerMonth !== null && views !== null && likes !== null && song !== null){
                    SongStatsContent(viewsPerMonth!!, views!!, likes!!, song!!)
                }
            }
        }
    )
}

@Composable
private fun SongStatsContent(viewsPerMonth: Array<Int>, views: Int, likes: Int, song: SongResponse){
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
        text = song.name,
        fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onPrimary
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = "Likes: $likes",
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )

        Text(
            text = "Plays: $views",
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )
    }

    LineGraph(
        xAxisData = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec").map {
            GraphData.String(it)
        },
        yAxisData = viewsPerMonth.toList(),
        style = graphStyle
    )
}

