package com.example.recordily_client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recordily_client.R

@Composable
fun ExitBar(navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "arrow back",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .padding(top = dimensionResource(id = R.dimen.padding_medium))
                .size(30.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}