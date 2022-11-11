package com.example.recordily_client.pages.artist

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.Header
import com.example.recordily_client.components.TopNavBar
import com.example.recordily_client.navigation.TopNavItem
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.ViewStatsViewModel
import com.jaikeerthick.composable_graphs.color.BarGraphColors
import com.jaikeerthick.composable_graphs.composables.BarGraph
import com.jaikeerthick.composable_graphs.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.style.BarGraphVisibility

@Composable
fun ViewsStatsPage(navController: NavController){
    val pageOptions = listOf(
        TopNavItem.HomePage, TopNavItem.ViewsStatsPage, TopNavItem.SongsStatsPage
    )
    val loginViewModel: LoginViewModel = viewModel()
    val viewStatsViewModel: ViewStatsViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    viewStatsViewModel.getViewsPerMonth(token)

    val views by viewStatsViewModel.viewsResultLiveData.observeAsState()

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TopNavBar(
                    pageOptions = pageOptions,
                    currentPage = R.string.view_stats,
                    navController = navController
                )

                views?.let { it -> ViewsContent(it) }
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun ViewsContent(views: Array<Int>) {
    val graphStyle = BarGraphStyle(
        visibility = BarGraphVisibility(
            isYAxisLabelVisible = true
        ),
        colors = BarGraphColors(
            clickHighlightColor = MaterialTheme.colors.primary,
            fillGradient = Brush.verticalGradient(
                listOf(
                    colorResource(id = R.color.primary_lighter),
                    MaterialTheme.colors.primaryVariant,
                    MaterialTheme.colors.primary,
                    colorResource(id = R.color.primary_darker)
                )
            )
        ),
        paddingValues = PaddingValues(dimensionResource(id = R.dimen.padding_medium))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.padding_very_large)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BarGraph(
            dataList = views.toList(),
            header = {
                Text(
                    text = stringResource(id = R.string.view_stats),
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            style = graphStyle
        )
    }
}

