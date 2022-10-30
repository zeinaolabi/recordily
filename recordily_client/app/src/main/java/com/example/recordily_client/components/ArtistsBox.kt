package com.example.recordily_client.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen

@Composable
fun ArtistsBox(title: String, navController: NavController){
    Column(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .shadow(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.surface)
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .horizontalScroll(ScrollState(0)),
            verticalAlignment = Alignment.CenterVertically
        ){
            for (i in 1..5) {
                ArtistsBoxContent{
                    navController.navigate(Screen.ArtistProfilePage.route)
                }
            }
        }
    }
}

@Composable
fun ArtistsBoxContent(onClick: () -> (Unit)){
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            .clickable {
                onClick()
            }
    ){
        Image(
            painter = painterResource(R.drawable.recordily_dark_logo),
            contentDescription = "logo",
            modifier = Modifier
                .size(115.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Artist name",
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
            color = MaterialTheme.colors.onPrimary
        )
    }
}