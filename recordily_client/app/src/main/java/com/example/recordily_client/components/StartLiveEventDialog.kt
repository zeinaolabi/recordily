package com.example.recordily_client.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R

@Composable
fun StartLiveEventDialog(isOpen: MutableState<Boolean>, eventName: MutableState<String>){
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
                        //Navigate to event page
                        isOpen.value = false
                    }
                )
            }
        }
    )
}