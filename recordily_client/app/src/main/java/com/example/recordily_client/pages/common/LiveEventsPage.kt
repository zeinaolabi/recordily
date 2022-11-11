package com.example.recordily_client.pages.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo

private val liveEventName = mutableStateOf("")
private val openDialog = mutableStateOf(false)

@Composable
fun CommonLiveEventsPage(navController: NavController){

    Scaffold(
        topBar = { Header(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            ){
                LiveEventsContent()

                for( i in 1..3){
                    LiveEventCard{
                        navigateTo(
                            navController,
                            Screen.LiveEventPage.route + '/' + '1',
                            Screen.LiveEventsPage.route
                        )
                    }
                }
            }

            if(openDialog.value){
                StartLiveEventDialog(openDialog, liveEventName)
            }
        },
        bottomBar = { BottomNavigationBar(navController) }
    )
}

@Composable
private fun LiveEventsContent(){
    LargeRoundButton(
        text = stringResource(id = R.string.start_live),
        onClick = {
            openDialog.value = true
        }
    )

    Text(
        text = stringResource(id = R.string.current_events),
        fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    )
}
