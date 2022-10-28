package com.example.recordily_client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R

@Composable
fun RoundButton(text:String, onClick: () -> Unit){
    Button(modifier = Modifier
        .height(48.dp)
        .width(120.dp)
        .clip(MaterialTheme.shapes.large)
        .background(MaterialTheme.colors.secondary),
        onClick = { onClick() }) {
        Text(text = text)
    }
}

@Composable
fun SmallRoundButton(text:String, onClick: () -> (Unit)){
    Button(modifier = Modifier
        .height(35.dp)
        .width(90.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colors.primary),
        onClick = { onClick() })
    {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun MediumRoundButton(text:String, onClick: () -> (Unit)){
    Button(modifier = Modifier
        .height(40.dp)
        .width(120.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colors.primary),
        onClick = { onClick() })
    {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun LargeRoundButton(text:String, onClick: () -> (Unit)){
    Button(modifier = Modifier
        .height(48.dp)
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.large)
        .background(MaterialTheme.colors.primary),
        onClick = { onClick() }) {
        Text(text = text)
    }
}