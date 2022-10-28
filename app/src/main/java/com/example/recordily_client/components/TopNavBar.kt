package com.example.recordily_client.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.recordily_client.R

@Composable
fun TopNavBar(currentPage: String){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = Stroke.DefaultMiter * 1.5f
                val y = size.height - strokeWidth/10 + 20

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
        PageOptions(currentPage)
    }

}

@Composable
fun PageOptions(currentPage: String){
    val pageOptions = listOf(stringResource(R.string.home), stringResource(R.string.view_stats), stringResource(R.string.song_stats))
    val secondaryColor = MaterialTheme.colors.secondary

    pageOptions.forEach { text ->
        Row(){
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}