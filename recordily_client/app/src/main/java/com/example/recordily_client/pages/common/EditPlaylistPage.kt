package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.responses.PlaylistResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.EditPlaylistViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)
private var image: File? = null
private var imgBitmap: MutableState<Bitmap?> = mutableStateOf(null)
private var progressVisibility = mutableStateOf(false)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditPlaylistPage(navController: NavController, playlistID: String) {
    val editPlaylistModel: EditPlaylistViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    editPlaylistModel.getPlaylist(token, playlistID)
    val playlist = editPlaylistModel.playlistResultLiveData.observeAsState()

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
                    playlist.value?.let { it ->
                        CreatePlaylistContent(navController, it, token, editPlaylistModel)
                    }
                }
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            errorMessage.value = ""
            visible.value = false
            imgBitmap.value = null
            progressVisibility.value = false
        }
    }
}

@Composable
private fun CreatePlaylistContent(navController: NavController, playlist: PlaylistResponse, token: String, editPlaylistModel: EditPlaylistViewModel) {
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val intent = result.data
                val dir = File(Environment.getExternalStorageDirectory().absolutePath)

                val fileName = intent?.data?.lastPathSegment?.replace("primary:", "").toString()
                val file = File(dir, fileName)

                image = file

                imgBitmap.value = BitmapFactory.decodeFile(image!!.absolutePath)
            }
        }

    val coroutineScope = rememberCoroutineScope()
    val playlistName = remember { mutableStateOf(playlist.name) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){}
    val context = LocalContext.current

    Image(
        painter =
        when {
            imgBitmap.value != null -> {
                rememberImagePainter(data = imgBitmap.value)
            }
            playlist.picture != null -> {
                rememberAsyncImagePainter(playlist.picture)
            }
            else -> {
                painterResource(id = R.drawable.profile_picture)
            }
        },
        contentDescription = "logo",
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
            .clickable {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) -> {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "image/*"
                        startForResult.launch(intent)
                    }
                    else -> {
                        permissionLauncher.launch(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    }
                }
            },
        contentScale = ContentScale.FillBounds
    )

    TextField(
        input = playlistName,
        text = stringResource(id = R.string.playlist_name),
        visibility = true
    )

    if (!progressVisibility.value) {
        MediumRoundButton(
            text = stringResource(id = R.string.save),
            onClick = {
                progressVisibility.value = true

                val multipart =
                    if (image != null) {
                        MultipartBody.Part.createFormData(
                            "picture", "picture",
                            RequestBody.create(
                                "image/*".toMediaTypeOrNull(),
                                image!!
                            )
                        )
                    } else {
                        null
                    }

                coroutineScope.launch {
                    val isEdited = editPlaylistModel.editPlaylist(
                        token,
                        playlist.id.toString(),
                        playlistName.value,
                        multipart
                    )

                    if (!isEdited) {
                        errorMessage.value = "Network Error"
                        visible.value = true
                        progressVisibility.value = false
                        return@launch
                    }

                    errorMessage.value = "Successfully Edited!"
                    visible.value = true
                    progressVisibility.value = false
                }

            }
        )
    } else {
        CircularProgressBar()
    }

    Text(
        text = stringResource(id = R.string.delete_playlist),
        fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier.clickable
        {
            coroutineScope.launch {
                val isDeleted = editPlaylistModel.deletePlaylist(token, playlist.id.toString())

                if (!isDeleted) {
                    errorMessage.value = "Delete Failed"
                    visible.value = true
                    return@launch
                }

                navController.navigate(Screen.PlaylistsPage.route) {
                    popUpTo(0)
                }

            }
        }
    )

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
