package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.LiveEventsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val liveEventName = mutableStateOf("")
private val openDialog = mutableStateOf(false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommonLiveEventsPage(navController: NavController){
    val liveEventsViewModel: LiveEventsViewModel = viewModel()

    liveEventsViewModel.getLiveEvents()

    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()
    val id = userCredentials.getID()
    val userType = userCredentials.getType()
    val lives = liveEventsViewModel.displayLives()
    var artist: UserResponse?

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .padding(bottom = dimensionResource(id = R.dimen.padding_very_large))
                    .verticalScroll(ScrollState(0))

            ){

                LiveEventsContent(userType)

                lives.values.let { lives ->
                    for(live in lives){

                        runBlocking {
                            artist = liveEventsViewModel.getArtist(token, live.hostID.toString())
                        }

                        artist?.name?.let { artistName ->
                            artist?.profile_picture?.let { picture ->
                                LiveEventCard(live.name, picture, artistName) {
                                    navigateTo(
                                        navController,
                                        Screen.LiveEventPage.route + '/' + live.id
                                                + '/' + live.hostID + '/' + live.name,
                                        Screen.LiveEventsPage.route
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if(openDialog.value){
                StartLiveEventDialog(
                    openDialog,
                    liveEventName,
                    navController,
                    id,
                    token,
                    liveEventsViewModel
                )
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun LiveEventsContent(userType: Int){
    val artistType = 0

    if(userType == artistType){
        LargeRoundButton(
            text = stringResource(id = R.string.start_live),
            onClick = {
                openDialog.value = true
            }
        )
    }

    Text(
        text = stringResource(id = R.string.current_events),
        fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    )
}

@Composable
fun StartLiveEventDialog(
    isOpen: MutableState<Boolean>,
    eventName: MutableState<String>,
    navController: NavController,
    id: Int,
    token: String,
    liveEventsViewModel: LiveEventsViewModel
){
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = {
            isOpen.value = false
        },
        backgroundColor = MaterialTheme.colors.surface,
        title = {
            Text(
                text = stringResource(id = R.string.start_event),
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large))
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextField(
                    input = eventName,
                    text = stringResource(id = R.string.event_name),
                    visibility = true
                )
            }

        },
        confirmButton = {
            Row(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ){
                SmallRoundButton(
                    text = stringResource(id = R.string.start),
                    onClick = {
                        coroutineScope.launch {
                            val key = liveEventsViewModel.addLiveEvent(
                                token,
                                liveEventName.value,
                                id
                            )

                            if(key !== null){
                                navigateTo(
                                    navController = navController,
                                    destination = Screen.LiveEventPage.route
                                            + '/' + key + '/' + id + '/' + liveEventName.value,
                                    popUpTo = Screen.LiveEventsPage.route
                                )

                                liveEventName.value = ""
                                isOpen.value = false
                            } else {
                                Toast.makeText(
                                    liveEventsViewModel.context,
                                    "Failed to create live event",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
            }
        }
    )
}
