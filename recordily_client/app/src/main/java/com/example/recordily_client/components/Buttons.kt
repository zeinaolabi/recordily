package com.example.recordily_client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
        .height(35.dp)
        .width(90.dp)
        .clip(MaterialTheme.shapes.medium)
        .innerShadow(
            blur = 7.dp,
            color = MaterialTheme.colors.primaryVariant,
            cornersRadius = 50.dp,
            offsetX = (-5.5).dp,
            offsetY = (-3.5).dp
        )
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
        .innerShadow(
            blur = 12.dp,
            color = MaterialTheme.colors.primaryVariant,
            cornersRadius = 50.dp,
            offsetX = (-5.5).dp,
            offsetY = (-3.5).dp
        )
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
            .height(35.dp)
            .width(80.dp)
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(20.dp)),
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