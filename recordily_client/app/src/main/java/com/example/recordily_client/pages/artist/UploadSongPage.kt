package com.example.recordily_client.pages.artist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recordily_client.view_models.UploadSongViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.os.Environment
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.view_models.LoginViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.FileOutputStream

import java.io.File

import java.io.BufferedInputStream

import java.io.FileInputStream
import kotlin.collections.ArrayList

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)
private var image: File = File("")
private var imgBitmap: MutableState<Bitmap?> = mutableStateOf(null)
private val songName = mutableStateOf("")
private val fileName = mutableStateOf("")
private var chunks: File = File("")
private var selectedAlbum: MutableState<Int?> = mutableStateOf(null)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UploadSongPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.upload_song)) },
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
                    UploadSongContent()
                }                
            }
        }
    )
}

@SuppressLint("UsableSpace")
@Composable
private fun UploadSongContent(){
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

    val coroutinesScope = rememberCoroutineScope()
    val uploadSongViewModel: UploadSongViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()
    val id = loginViewModel.sharedPreferences.getInt("id", -1)

    Image(
        painter =
        if(imgBitmap.value != null) {
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
            .clickable {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startForResult.launch(intent)
            },
        contentScale = ContentScale.FillBounds
    )
    
    TextField(
        input = songName, 
        text = stringResource(id = R.string.song_name),
        visibility = true
    )

    DropDownAlbumMenu(uploadSongViewModel, token)

    PickAudioRow()

    MediumRoundButton(text = stringResource(id = R.string.save), onClick = {
        if(songName.value == "" || image == File("")){
            errorMessage.value = "Empty Field"
            visible.value = true
            return@MediumRoundButton
        }

        val files = splitFile(chunks)
        val songID = System.currentTimeMillis().toString() + id

        files.forEachIndexed { index, file ->
            val uploadSongRequest = UploadSongRequest(
                user_id =  id,
                name = songName.value,
                image ="test",
                chunks_size = files.size,
                chunk_num = index,
                song_id = songID,
                album_id = selectedAlbum.value
            )

            coroutinesScope.launch {
                val isCreated = uploadSongViewModel.uploadSong(
                    token = token,
                    uploadSongRequest = uploadSongRequest,
                    song = MultipartBody.Part.createFormData(
                        "file",
                        songName.value,
                        RequestBody.create("audio/*".toMediaTypeOrNull(),
                            file)
                    ),
                    image = MultipartBody.Part.createFormData(
                        "picture",
                        "picture",
                        RequestBody.create("image/*".toMediaTypeOrNull(),
                            image
                        )
                    )
                )

                if(!isCreated){
                    errorMessage.value = "Network error"
                    visible.value = true
                    return@launch
                }
            }

        }

        errorMessage.value = "Successfully Created!"
        visible.value = true
    })

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

@Composable
private fun DropDownAlbumMenu(uploadSongViewModel: UploadSongViewModel, token: String){
    var expanded by remember { mutableStateOf(false) }
    var selectedAlbumName by remember { mutableStateOf("Single") }
    val albumList: HashMap<Int?, String> = HashMap()
    albumList[null] = "Single"

    uploadSongViewModel.getAlbums(token)
    val albums = uploadSongViewModel.albumsResultLiveData.observeAsState()

    if(albums.value != null) {
        for (album in albums.value!!) {
            albumList[album.id] = album.name
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(60.dp)
            .border(1.dp, MaterialTheme.colors.secondary, RoundedCornerShape(10.dp))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .clickable {
                expanded = !expanded
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedAlbumName,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            color = colorResource(R.color.darker_gray),
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(25.dp)
        )

        DropdownMenu(
            expanded = expanded, onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .background(colorResource(id = R.color.darker_gray))
        ) {
            albumList.keys.forEach { albumID ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedAlbumName = albumList[albumID].toString()
                        selectedAlbum.value = albumID
                    }
                ) {
                    Text(
                        text = albumList[albumID].toString(),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun PickAudioRow(){
    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val intent = result.data
            val dir = File(Environment.getExternalStorageDirectory().absolutePath)

            fileName.value = intent?.data?.lastPathSegment?.replace("primary:", "").toString()
            val file = File(dir, fileName.value)
            file.createNewFile()

            chunks = file

        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(0.9f)
    ){

        Text(
            text = stringResource(id = R.string.upload_new_song) + " " + fileName.value,
            fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.fillMaxWidth(0.6f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        SmallTealButton(text = "Upload", onClick = {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/mpeg"
            startForResult.launch(intent)
        })

    }
}

fun splitFile(file: File): ArrayList<File> {
    var partCounter = 1
    val sizeOfFiles = 1024 * 100

    val buffer = ByteArray(sizeOfFiles)
    val fileName: String = file.name
    val fileList = ArrayList<File>()

    FileInputStream(file).use { fis ->
        BufferedInputStream(fis).use { bis ->
            var bytesAmount = 0
            while (bis.read(buffer).also { it -> bytesAmount = it } > 0) {

                //Write each chunk of data into separate file with different number in name
                val filePartName = String.format("%s.%03d", fileName, partCounter++)
                val newFile = File(file.parent, filePartName)

                newFile.createNewFile()
                FileOutputStream(newFile).use { out -> out.write(buffer, 0, bytesAmount) }

                fileList.add(newFile)
            }
        }
    }

    return fileList
}
