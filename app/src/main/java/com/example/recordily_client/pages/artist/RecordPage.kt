package com.example.recordily_client.pages.artist

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.innerShadow
import com.example.recordily_client.view_models.RecordViewModel
import kotlinx.coroutines.delay

val recordState = mutableStateOf(false)
val pausedState = mutableStateOf(false)
val currentTime = mutableStateOf(0L)

@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordPage(navController: NavController){
    val recordViewModel : RecordViewModel = viewModel()

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

            RecordContent(recordViewModel)

            RecordTimer()

            RecordButtonsRow(recordViewModel)
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
fun RecordContent(recordViewModel: RecordViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if(recordState.value && !pausedState.value){
            WaveRecordAnimation(recordViewModel)
        }
        else{
            RecordButton(recordViewModel)
        }

    }
}


@Composable
fun RecordTimer(){
    val minutes = currentTime.value / 60L
    val seconds = currentTime.value % 60000L

    LaunchedEffect(key1 = currentTime.value, key2 = recordState.value, key3 = pausedState.value) {
        if (recordState.value && !pausedState.value) {
            delay(100L)
            currentTime.value += 100L
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Text(
            text = String.format("%d:%02d", minutes / 1000L, seconds / 1000L),
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
                if(pausedState.value){
                    recordViewModel.resumeRecording()
                    pausedState.value = false
                }
                else{
                    recordViewModel.recordAudio()
                    recordState.value = true
                }

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

@ExperimentalAnimationApi
@Composable
fun RecordButtonsRow(recordViewModel: RecordViewModel){

    AnimatedVisibility(
        pausedState.value,
        enter = fadeIn(0.4f) + expandIn(expandFrom = Alignment.TopStart),
        exit = fadeOut(animationSpec = tween(durationMillis = 250)) + shrinkOut(shrinkTowards = Alignment.TopStart)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Delete",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                modifier = Modifier.clickable {
                    currentTime.value = 0L
                    recordViewModel.deleteRecording()
                    pausedState.value = false
                    recordState.value = false
                }
            )

            Text(
                text = "Save",
                color = MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Medium,
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                modifier = Modifier.clickable {
                    //Navigate to save song page
                    currentTime.value = 0L
                    recordViewModel.stopRecording()
                    pausedState.value = false
                    recordState.value = false
                }
            )
        }
    }
}

@Composable
fun WaveRecordAnimation(recordViewModel: RecordViewModel){
    val waves = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(4000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart,
    )

    waves.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = animationSpec
            )
        }
    }

    val dys = waves.map { it.value }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                recordViewModel.pauseRecording()
                pausedState.value = true
            },
        contentAlignment = Center
    ) {

        dys.forEach { dy ->
            Box(
                Modifier
                    .size(70.dp)
                    .align(Center)
                    .graphicsLayer {
                        scaleX = dy * 4 + 1
                        scaleY = dy * 4 + 1
                        alpha = 1 - dy
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.primary, shape = CircleShape)
                )
            }
        }

        Box(
            Modifier
                .size(80.dp)
                .align(Center)
                .background(color = MaterialTheme.colors.primary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.record_logo),
                "mic",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(50.dp)
                    .align(Center)
            )
        }

    }
}
