package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.recordily_client.R

@Composable
fun ProfileHeader(navController: NavController){
    Row(){
        Image(
            painter = painterResource(id = R.drawable.recordily_dark_logo),
            contentDescription = "profile picture"
        )


    }
}

@Composable
fun profileInfo(navController: NavController){

}