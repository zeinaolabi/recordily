package com.example.recordily_client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
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
        .innerShadow(
            blur = 15.dp,
            color = MaterialTheme.colors.primaryVariant,
            cornersRadius = 50.dp,
            offsetX = (-8.5).dp,
            offsetY = (-5.5).dp
        )
        .background(MaterialTheme.colors.secondary),
        onClick = { onClick() }) {
        Text(text = text)
    }
}

@Composable
fun SmallRoundButton(text:String, onClick: () -> (Unit)){
    Button(modifier = Modifier
        .innerShadow(
            blur = 7.dp,
            color = MaterialTheme.colors.primaryVariant,
            cornersRadius = 10.dp,
            offsetX = (-5.5).dp,
            offsetY = (-3.5).dp
        )
        .clip(MaterialTheme.shapes.small)
        .height(30.dp)
        .width(80.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        onClick = { onClick() })
    {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun MediumRoundButton(text:String, onClick: () -> (Unit)){
    Button(modifier = Modifier
        .clip(MaterialTheme.shapes.medium)
        .innerShadow(
            blur = 12.dp,
            color = MaterialTheme.colors.primaryVariant,
            cornersRadius = 50.dp,
            offsetX = (-5.5).dp,
            offsetY = (-3.5).dp
        )
        .height(40.dp)
        .width(119.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
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
        .height(45.dp)
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.small)
        .innerShadow(
            blur = 12.dp,
            color = MaterialTheme.colors.primaryVariant,
            cornersRadius = 10.dp,
            offsetX = (-5.5).dp,
            offsetY = (-3.5).dp
        )
        .background(MaterialTheme.colors.primary),
        onClick = { onClick() }) {
        Text(text = text)
    }
}

@Composable
fun SmallTealButton(text:String, onClick: () -> (Unit)){
    Button(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .bounceClick()
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(20.dp))
            .height(35.dp)
            .width(80.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        interactionSource = remember { NoRippleInteractionSource() },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
        ),
        onClick = { onClick() })
    {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun FloatingButton(onClick: () -> (Unit)){
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_very_large)
                )
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                .align(Alignment.BottomEnd),
            backgroundColor = MaterialTheme.colors.primary,
            onClick = {onClick()}
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add",
                tint = Color.White
            )
        }
    }
}