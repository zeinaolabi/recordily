package com.example.recordily_client.pages.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.pages.common.BoxContent

@Composable
fun RecordPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_large)
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            ExitPage(navController)
        }
    }
}

@Composable
fun ExitPage(navController: NavController){
    Row {
        Icon(
            Icons.Default.Close,
            contentDescription = "Exit",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )

        Text(
            text = "Recording",
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun RecordContent(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "2:17",
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
        )
        
        Box(
            modifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .clickable { },
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(id = R.drawable.record_logo),
                contentDescription = "Record Logo",
                modifier = Modifier.size(250.dp)
            )
        }


    }
}