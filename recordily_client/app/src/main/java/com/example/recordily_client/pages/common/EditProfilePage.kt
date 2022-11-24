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
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.EditProfileViewModel
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
fun EditProfilePage(navController: NavController) {
    val editProfileViewModel: EditProfileViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    editProfileViewModel.getUserInfo(token)
    val userInfo by editProfileViewModel.userInfoResultLiveData.observeAsState()

    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.edit_profile)) },
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
                    userInfo?.let { it -> EditProfileContent(it, editProfileViewModel, token) }
                }
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            errorMessage.value = ""
            visible.value = false
            imgBitmap.value = null
        }
    }
}

@Composable
private fun EditProfileContent(
    userInfo: UserResponse,
    editProfileViewModel: EditProfileViewModel,
    token: String
){
    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result: ActivityResult ->
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
    val name = remember { mutableStateOf(userInfo.name ?: "") }
    val bio = remember { mutableStateOf(userInfo.biography ?: "") }

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
            userInfo.profile_picture != null -> {
                rememberAsyncImagePainter(userInfo.profile_picture)
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
        contentScale = ContentScale.Crop
    )

    TextField(
        input = name,
        text = stringResource(id = R.string.name),
        visibility = true
    )

    TextField(
        input = bio,
        text = stringResource(id = R.string.bio),
        visibility = true
    )

    if(!progressVisibility.value) {
        MediumRoundButton(
            text = stringResource(id = R.string.save),
            onClick = {
                progressVisibility.value = true
                visible.value = false

                if (name.value == "" || bio.value == "") {
                    errorMessage.value = "Empty Field"
                    visible.value = true
                    progressVisibility.value = false
                    return@MediumRoundButton
                }

                coroutineScope.launch {
                    val multipart =
                        if (image != null) {
                            MultipartBody.Part.createFormData(
                                "profile_picture", "profile_picture",
                                RequestBody.create(
                                    "image/*".toMediaTypeOrNull(),
                                    image!!
                                )
                            )
                        } else {
                            null
                        }

                    val isEdited = editProfileViewModel.editProfile(
                        token,
                        name.value,
                        bio.value,
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
        SimpleCircularProgressBar()
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
