package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.components.RoundSendButton
import com.example.recordily_client.components.SongsPopUp
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.requests.MessageRequest
import com.example.recordily_client.responses.ChatMessage
import com.example.recordily_client.responses.SongResponse
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.LiveEventViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private val isPlaying = mutableStateOf(false)
private val progress = mutableStateOf(0f)
private val message = mutableStateOf("")
private val input = mutableStateOf("")
private val popUpVisibility = mutableStateOf(false)
private val currentlyPlaying = mutableStateOf("")
private val senderInfo: HashMap<Int, UserResponse> =
    mutableMapOf<Int, UserResponse>() as HashMap<Int, UserResponse>

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LiveEventPage(navController: NavController, liveID: String, hostID: String, liveName: String){
    val liveEventViewModel: LiveEventViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()
    val userID = userCredentials.getID()
    val coroutineScope = rememberCoroutineScope()

    liveEventViewModel.getHostImage(token, hostID)
    liveEventViewModel.getMessages(liveID)
    liveEventViewModel.getSong(liveID)
    liveEventViewModel.isLive(liveID)

    val hostPicture = liveEventViewModel.hostPictureResultLiveData.observeAsState()
    val chatMessage = liveEventViewModel.messagesResultLiveData.observeAsState()
    val song = liveEventViewModel.songResultLiveData.observeAsState()
    val songInfo = liveEventViewModel.songInfoResultLiveData.observeAsState()
    val isLive = liveEventViewModel.isLiveResultLiveData.observeAsState()
    val chatMessages = liveEventViewModel.displayMessages()

    isLive.value?.let {
        if(!it){
            navigateTo(
                navController = navController,
                destination = Screen.LiveEventsPage.route,
                popUpTo = Screen.LiveEventsPage.route
            )

            liveEventViewModel.stopPlayingSong()
            liveEventViewModel.clearMessages()
        }
    }

    chatMessage.value?.let {
        coroutineScope.launch {
            if (!senderInfo.containsKey(it.fromID)) {
                senderInfo[it.fromID] = liveEventViewModel.getArtist(token, it.fromID.toString())
            }
        }
    }

    song.value?.let { songID ->
        if(song.value != "") {
            liveEventViewModel.getSongInfo(token, songID)

            songInfo.value?.let { songInfo ->
                if (currentlyPlaying.value != songID) {
                    liveEventViewModel.stopPlayingSong()
                    isPlaying.value = false
                }

                liveEventViewModel.startPlayingSong(songInfo.path)
                isPlaying.value = true
                currentlyPlaying.value = songInfo.id.toString()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            LiveHeader(hostPicture.value, liveName)

            SongPlaying(songInfo.value, liveEventViewModel, hostID.toInt(), userID)

            ChatSection(userID, liveEventViewModel, chatMessages, token)
        }

        SendMessageRow(token, userID, liveID, liveEventViewModel)

        AnimatedVisibility(
            visible = popUpVisibility.value,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
        ) {
            SongsPopUp(
                input = input,
                popUpVisibility = popUpVisibility,
                liveEventID = liveID,
                onClick = {
                    liveEventViewModel.endLive(liveID)
                    navController.popBackStack()
                }
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            popUpVisibility.value = false
            message.value = ""
            input.value = ""
            liveEventViewModel.stopPlayingSong()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ChatSection(
    id: Int,
    liveEventViewModel: LiveEventViewModel,
    chatMessages: LinkedHashMap<String, ChatMessage>,
    token: String
){
    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxHeight(0.88f)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .verticalScroll(ScrollState(rememberScrollState().maxValue))
    ){
        for(message in chatMessages.values){

            runBlocking {
                if (!senderInfo.containsKey(message.fromID)) {
                    senderInfo[message.fromID] =
                        liveEventViewModel.getArtist(token, message.fromID.toString())
                }
            }

            if (message.fromID == id) {
                Row(modifier = Modifier.align(Alignment.End)) {
                    ToMessage(
                        message = message.message,
                        time = liveEventViewModel.convertDate(message.createdAt, "hh:mm"),
                        picture = senderInfo[message.fromID]?.profile_picture
                    )
                }
            } else {
                Row(modifier = Modifier.align(Alignment.Start)) {
                    FromMessage(
                        message = message.message,
                        time = liveEventViewModel.convertDate(message.createdAt, "hh:mm"),
                        name = senderInfo[message.fromID]?.name,
                        picture = senderInfo[message.fromID]?.profile_picture
                    )
                }
            }

        }
    }
}

@Composable
private fun SendMessageRow(
    token: String,
    id: Int,
    liveEventID: String,
    liveEventViewModel: LiveEventViewModel
){
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
                if(message.value === "") {
                    return@launch
                }

                val liveEventRequest = MessageRequest(message.value, liveEventID)
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
private fun LiveHeader(hostInfo: UserResponse?, liveName: String){
    val hostPicture = hostInfo?.profile_picture
    val hostName = hostInfo?.name

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
private fun SongPlaying(
    song: SongResponse?,
    liveEventViewModel: LiveEventViewModel,
    hostID: Int,
    userID: Int
){
    val duration = remember { mutableStateOf(0L) }

    song?.path?.let { path ->
         liveEventViewModel.getDuration(path)?.let { durationTime ->
             LaunchedEffect(key1 = isPlaying.value) {
                 duration.value = durationTime

                 val timer = object : CountDownTimer(duration.value, 100) {
                     override fun onTick(millisUntilFinished: Long) {
                         val finishedSeconds = duration.value - millisUntilFinished
                         val total = finishedSeconds / duration.value.toFloat()
                         progress.value = total

                         if(currentlyPlaying.value !== song.id.toString()){
                             this.cancel()
                         }
                     }

                     override fun onFinish() {
                         this.cancel()
                         this.start()
                     }
                 }

                 if (isPlaying.value) {
                     timer.start()
                 }
             }
         }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(5.dp)
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
            .clickable {
                if (hostID == userID) {
                    popUpVisibility.value = true
                }
            }
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter =
            if(song?.picture !== null && song.picture != ""){
                rememberAsyncImagePainter(song.picture)
            }
            else {
                painterResource(R.drawable.recordily_dark_logo)
            },
            contentDescription = "Song picture",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = song?.name ?: "No Song Playing",
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            LinearProgressIndicator(
                color = Color.White,
                progress = progress.value,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
