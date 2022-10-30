package com.example.recordily_client.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.dimensionResource
import com.example.recordily_client.R

@Composable
fun HorizontalLine(){
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
}