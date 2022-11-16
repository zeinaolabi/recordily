package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.view_models.CreatePlaylistViewModel
import com.example.recordily_client.view_models.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)
private val playlistName = mutableStateOf("")
private var image: File = File("")
private var imgBitmap: MutableState<Bitmap?> = mutableStateOf(null)
private var progressVisibility = mutableStateOf(false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreatePlaylistPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.new_playlist)) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
                ){
                    CreatePlaylistContent()
                }
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            errorMessage.value = ""
            playlistName.value = ""
            visible.value = false
            imgBitmap.value = null
            progressVisibility.value = false
        }
    }
}

@Composable
private fun CreatePlaylistContent(){
    val logo = if (isSystemInDarkTheme()) R.drawable.recordily_gray_logo else R.drawable.recordily_light_mode
    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val intent = result.data
            val dir = File(Environment.getExternalStorageDirectory().absolutePath)

            val fileName = intent?.data?.lastPathSegment?.replace("primary:", "").toString()
            val file = File(dir, fileName)

            image = file

            imgBitmap.value = BitmapFactory.decodeFile(image.absolutePath)
        }
    }
    val createPlaylistModel: CreatePlaylistViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    val coroutineScope = rememberCoroutineScope()

    Image(
        painter = if(imgBitmap.value != null) {
            rememberImagePainter(data = imgBitmap.value)
        }
        else{
            painterResource(id = logo)
            },
        contentDescription = "logo",
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
            .clickable{
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startForResult.launch(intent)
            },
        contentScale = ContentScale.FillBounds
    )

    TextField(
        input = playlistName,
        text = stringResource(id = R.string.playlist_name),
        visibility = true
    )

    if(progressVisibility.value) {
        MediumRoundButton(
            text = stringResource(id = R.string.save),
            onClick = {
                if(playlistName.value == "" || image == File("")){
                    errorMessage.value = "Empty Field"
                    visible.value = true
                    return@MediumRoundButton
                }

                coroutineScope.launch{
                    val isCreated = createPlaylistModel.addPlaylist(
                        token,
                        playlistName.value,
                        MultipartBody.Part.createFormData("picture", "picture",
                            RequestBody.create("image/*".toMediaTypeOrNull(),
                                image
                            )
                        )
                    )
                    if(!isCreated){
                        errorMessage.value = "Network Error"
                        visible.value = true
                        return@launch
                    }

                    errorMessage.value = "Successfully Created!"
                    visible.value = true
                }
            }
        )
    } else {
        CircularProgressBar()
    }

    AnimatedVisibility(
        visible = visible.value,
        enter = slideInHorizontally(
            initialOffsetX = { -40 }
        ),
        exit = slideOutHorizontally()
    ) {
        Text(
            text = errorMessage.value,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
    }
}