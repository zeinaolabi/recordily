package com.example.recordily_client.pages.common

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.recordily_client.components.BottomNavigationBar
import com.example.recordily_client.components.Header
import com.example.recordily_client.components.RoundSendButton
import com.example.recordily_client.components.TopNavBar

private val message = mutableStateOf("")

@Composable
fun LiveEventPage(navController: NavController, live_event_id: String){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(0f)
            .background(MaterialTheme.colors.background)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            LiveHeader()

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.88f)
                    .fillMaxWidth()
                    .background(Color.Red)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_large),
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ){

            }
        }
    }

    SendMessageRow()
}

@Composable
private fun SendMessageRow(){
    Row(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .zIndex(2f)
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .clip(MaterialTheme.shapes.large),
            value = message.value,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.message),
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                )
            },
            onValueChange = { message.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.darker_gray),
                cursorColor = Color.White,
                textColor = Color.White,
                unfocusedIndicatorColor = colorResource(id = R.color.darker_gray),
                focusedIndicatorColor = colorResource(id = R.color.darker_gray)
            )
        )

        RoundSendButton(onClick = {})
    }
}


@Composable
private fun LiveHeader(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(
            text = "Live Name",
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )
        
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
        ){
            Text(
                text = "Live Hosted by",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
            
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Host image",
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                    .size(35.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
            )

            Text(
                text = "Host name ",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}