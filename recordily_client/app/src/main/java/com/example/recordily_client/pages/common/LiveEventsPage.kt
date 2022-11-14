package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import com.example.recordily_client.view_models.LiveEventsViewModel
import com.example.recordily_client.view_models.LoginViewModel

private val liveEventName = mutableStateOf("")
private val openDialog = mutableStateOf(false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CommonLiveEventsPage(navController: NavController){
    val loginViewModel: LoginViewModel = viewModel()
    val liveEventsViewModel: LiveEventsViewModel = viewModel()

    liveEventsViewModel.getLiveEvents()

    val id = loginViewModel.sharedPreferences.getInt("id", -1)
    val userType = loginViewModel.sharedPreferences.getInt("user_type_id", -1)
    val token = "Bearer" + loginViewModel.sharedPreferences.getString("token", "").toString()
    val lives by liveEventsViewModel.liveEventsResultLiveData.observeAsState()

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

                if(lives != null){
                    for(live in lives!!){

                        liveEventsViewModel.getArtist(token, live.hostID.toString())

                        val artist = liveEventsViewModel.artistInfoResultLiveData.observeAsState().value

                        artist?.name?.let { it ->
                            LiveEventCard(live.name, artist.profile_picture, it){
                                navigateTo(
                                    navController,
                                    Screen.LiveEventPage.route + '/' + live.id + '/' + live.hostID + '/' + live.name,
                                    Screen.LiveEventsPage.route
                                )
                            }
                        }
                    }
                }
            }

            if(openDialog.value){
                StartLiveEventDialog(openDialog, liveEventName, navController, id, liveEventsViewModel)
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
    liveEventsViewModel: LiveEventsViewModel
){
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
                        val key = liveEventsViewModel.addLiveEvent(liveEventName.value, id)

                        navigateTo(
                            navController = navController,
                            destination = Screen.LiveEventPage.route + '/' + key,
                            popUpTo = Screen.LiveEventsPage.route
                        )

                        liveEventName.value = ""
                        isOpen.value = false
                    }
                )
            }
        }
    )
}
