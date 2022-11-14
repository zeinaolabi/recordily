package com.example.recordily_client.pages.common

import android.text.format.DateFormat
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.components.RoundSendButton
import com.example.recordily_client.requests.MessageRequest
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.view_models.LiveEventViewModel
import com.example.recordily_client.view_models.LoginViewModel
import kotlinx.coroutines.launch

private val message = mutableStateOf("")

@Composable
fun LiveEventPage(live_event_id: String, host_id: String, live_name: String){
    val loginViewModel: LoginViewModel = viewModel()
    val liveEventViewModel: LiveEventViewModel = viewModel()
    val id = loginViewModel.sharedPreferences.getInt("id", -1)
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    liveEventViewModel.getHostImage(token, host_id)

    val hostPicture = liveEventViewModel.hostPictureResultLiveData.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            hostPicture.value?.let { LiveHeader(it, live_name) }

            SongPlaying()

            ChatSection(id, liveEventViewModel, token, live_event_id)
        }

        SendMessageRow(token, id, live_event_id, liveEventViewModel)
    }
}

@Composable
private fun ChatSection(id: Int, liveEventViewModel: LiveEventViewModel, token: String, live_event_id: String){

    liveEventViewModel.getMessages(live_event_id)

    val chatMessage = liveEventViewModel.messagesResultLiveData.observeAsState()
    val artist = liveEventViewModel.userInfoResultLiveData.observeAsState().value

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxHeight(0.88f)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .verticalScroll(ScrollState(rememberScrollState().maxValue))
    ){
        if(chatMessage.value != null){
//            for(message in chatMessage.value!!){
//                liveEventViewModel.getArtist(token, message.fromID.toString())
//
//                if (message.fromID == id) {
//                    Row(modifier = Modifier.align(Alignment.End)) {
//                        ToMessage(
//                            message = message.message,
//                            time = convertDate(message.createdAt, "hh:mm"),
//                            picture = artist?.profile_picture
//                        )
//                    }
//                } else {
//                    Row(modifier = Modifier.align(Alignment.Start)) {
//                        FromMessage(
//                            message = message.message,
//                            time = convertDate(message.createdAt, "hh:mm"),
//                            name = artist?.name,
//                            picture = artist?.profile_picture
//                        )
//                    }
//                }
//            }
        }
    }
}

@Composable
private fun SendMessageRow(token: String, id: Int, live_event_id: String, liveEventViewModel: LiveEventViewModel){
    val coroutinesScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_medium),
                horizontal = dimensionResource(id = R.dimen.padding_small)
            )
            .imePadding(),
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

        RoundSendButton(onClick = {
            coroutinesScope.launch {
                val liveEventRequest = MessageRequest(message.value, live_event_id)
                val messageSent = liveEventViewModel.sendMessage(token, liveEventRequest, id)

                if(!messageSent){
                    Toast.makeText(liveEventViewModel.context, "Failed to send message", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                message.value = ""
            }
        }
        )
    }
}


@Composable
private fun LiveHeader(hostInfo: UserResponse, liveName: String){
    val hostPicture = hostInfo.profile_picture
    val hostName = hostInfo.name

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = liveName,
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
                text = "Live Hosted By",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )

            Image(
                painter =
                if(hostPicture != null && hostPicture != ""){
                    rememberAsyncImagePainter(hostPicture)
                }
                else{
                    painterResource(id = R.drawable.profile_picture)
                },
                contentDescription = "Host image",
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                    .size(35.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
            )

            Text(
                text =
                if(hostName == null || hostName === ""){
                    "Username"
                } else {
                    "$hostName"
                },
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun ToMessage(message: String, time: String, picture: String?) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = dimensionResource(id = R.dimen.padding_small))
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.large)
                    .background(colorResource(id = R.color.darker_gray))
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_chat_padding),
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = message,
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    text = time,
                    fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_small))
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }

        Image(
            painter =
            if(picture != null && picture != ""){
                rememberAsyncImagePainter(picture)
            }
            else{
                painterResource(id = R.drawable.profile_picture)
            },
            contentDescription = "user profile",
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_very_small))
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun FromMessage(message: String, time: String, name: String?, picture: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
    ) {
        Image(
            painter =
            if(picture != null && picture != ""){
                rememberAsyncImagePainter(picture)
            }
            else{
                painterResource(id = R.drawable.profile_picture)
            },
            contentDescription = "user profile",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = name ?: "Username",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_chat_padding),
                        horizontal = dimensionResource(id = R.dimen.padding_medium)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = message,
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    text = time,
                    fontSize = dimensionResource(id = R.dimen.font_very_small).value.sp,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.padding_small))
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
private fun SongPlaying(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(5.dp)
            .background(MaterialTheme.colors.secondary)
            .padding(dimensionResource(id = R.dimen.padding_small)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = painterResource(id = R.drawable.recordily_dark_logo),
            contentDescription = "Song picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = "Song name",
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            LinearProgressIndicator(
                color = Color.White,
                progress = 0.7f,
                modifier = Modifier
                    .fillMaxWidth()

            )
        }

        Icon(
            painter = painterResource(id = R.drawable.heart),
            contentDescription = "like",
            modifier = Modifier
                .size(30.dp),
            tint = Color.White
        )
    }
}

fun convertDate(dateInMilliseconds: Long, dateFormat: String): String {
    return DateFormat.format(dateFormat, dateInMilliseconds).toString()
}
