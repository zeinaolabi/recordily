package com.example.recordily_client.pages.artist

import android.media.MediaRecorder
import android.media.metrics.LogSessionId
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.MediumRoundButton
import com.example.recordily_client.components.innerShadow
import com.example.recordily_client.view_models.RecordViewModel
import kotlinx.coroutines.delay

val recordState = mutableStateOf(false)

@RequiresApi(Build.VERSION_CODES.S)
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

            RecordContent()

            RecordButtonRow()
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
            text = stringResource(id = R.string.recording),
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordContent(){
    val recordViewModel : RecordViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Log.i("Test", recordState.value.toString())
        if(recordState.value){
            WaveRecordAnimation(recordViewModel)
        }
        else{
            RecordButton(recordViewModel)
        }

    }
}


@Composable
fun RecordButtonRow(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Text(
            text = "2:17",
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_large)),
            fontSize = dimensionResource(id = R.dimen.font_title).value.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordButton(recordViewModel: RecordViewModel){
    Box(
        modifier = Modifier
            .size(220.dp)
            .clip(CircleShape)
            .shadow(15.dp)
            .background(MaterialTheme.colors.primary)
            .innerShadow(
                blur = 25.dp,
                color = MaterialTheme.colors.primaryVariant,
                cornersRadius = 150.dp,
                offsetX = (-20.5).dp,
                offsetY = (-15.5).dp
            )
            .clickable {
                recordViewModel.recordAudio()
                recordState.value = true
            },
        contentAlignment = Center
    ){
        Icon(
            painter = painterResource(id = R.drawable.record_logo),
            contentDescription = "Record Logo",
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

