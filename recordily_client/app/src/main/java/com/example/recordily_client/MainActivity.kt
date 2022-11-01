package com.example.recordily_client

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recordily_client.navigation.SetupNavGraph
import com.example.recordily_client.ui.theme.Recordily_clientTheme

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Recordily_clientTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
