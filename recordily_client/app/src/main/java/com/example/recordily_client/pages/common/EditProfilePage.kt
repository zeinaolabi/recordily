package com.example.recordily_client.pages.common

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.view_models.EditProfileViewModel
import com.example.recordily_client.view_models.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)
private var image: File? = null
private var imgBitmap: MutableState<Bitmap?> = mutableStateOf(null)

@Composable
fun EditProfilePage(navController: NavController) {
    val editProfileViewModel: EditProfileViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

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
}

@Composable
private fun EditProfileContent(userInfo: UserResponse, editProfileViewModel: EditProfileViewModel, token: String){
    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
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

    Image(
        painter =
        if(imgBitmap.value != null) {
            rememberImagePainter(data = imgBitmap.value)
        }
        else if(userInfo.profile_picture != null){
            rememberAsyncImagePainter(userInfo.profile_picture)
        }
        else{
            painterResource(id = R.drawable.profile_picture)
        },
        contentDescription = "logo",
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
            .clickable {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startForResult.launch(intent)
            },
        contentScale = ContentScale.FillBounds
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

    MediumRoundButton(
        text = stringResource(id = R.string.save),
        onClick = {
            if(name.value == "" || bio.value == ""){
                errorMessage.value = "Empty Field"
                visible.value = true
                return@MediumRoundButton
            }

            coroutineScope.launch{
                val multipart =
                    if(image != null){
                        MultipartBody.Part.createFormData("profile_picture", "profile_picture",
                            RequestBody.create("image/*".toMediaTypeOrNull(),
                                image!!
                            )
                        )
                    } else{ null }

                val isEdited = editProfileViewModel.editProfile(
                    token,
                    name.value,
                    bio.value,
                    multipart
                )

                if(!isEdited){
                    errorMessage.value = "Network Error"
                    visible.value = true
                    return@launch
                }

                errorMessage.value = "Successfully Edited!"
                visible.value = false
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
