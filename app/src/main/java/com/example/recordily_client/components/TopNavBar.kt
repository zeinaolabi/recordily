package com.example.recordily_client.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Destination

@Composable
fun TopNavBar(pageOptions: List<Destination>, currentPage: String, navController: NavController){
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
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
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
    ){
        PageOptions(pageOptions, currentPage, navController)
    }

}

@Composable
fun PageOptions(pageOptions: List<Destination>, currentPage: String, navController: NavController){
    val secondaryColor = MaterialTheme.colors.secondary

    pageOptions.forEach { option ->
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate(option.route)
                }
                .conditional(currentPage == option.page) {
                    drawBehind {
                        val strokeWidth = Stroke.DefaultMiter * 5
                        val y = size.height - strokeWidth / 10 + 20

                        drawLine(
                            secondaryColor,
                            Offset(-10f, y),
                            Offset(size.width + 10, y),
                            strokeWidth,
                            cap = StrokeCap.Round
                        )
                    }
                }
        ){
            Text(
                text = option.page,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }

}