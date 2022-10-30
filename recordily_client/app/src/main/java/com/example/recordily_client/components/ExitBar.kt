package com.example.recordily_client.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R

@Composable
fun ExitBar(navController: NavController, title: String){
    Row(
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.padding_large))
            .fillMaxWidth()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "arrow back",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .size(25.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Text(
            text = title,
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.ExtraBold
        )
//
//        Text(
//            text = title,
//            fontSize = dimensionResource(id = R.dimen.font_large).value.sp,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colors.onPrimary
//        )
    }
}