package com.example.recordily_client.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(){
    CircularProgressIndicator(
        modifier = Modifier.size(40.dp),
        color = MaterialTheme.colors.primary,
        strokeWidth = 4.dp
    )
}