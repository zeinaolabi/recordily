package com.example.recordily_client.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R

@Composable
fun EmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.nothing_found),
            contentDescription = "not found",
            modifier = Modifier.size(60.dp),
            tint = Color.Unspecified
        )

        Text(
            text = message,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onPrimary
        )
    }
}