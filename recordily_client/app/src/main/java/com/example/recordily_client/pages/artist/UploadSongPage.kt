package com.example.recordily_client.pages.artist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.compose.ui.text.style.TextOverflow
import com.example.recordily_client.requests.UploadSongRequest
import com.example.recordily_client.view_models.LoginViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.FileOutputStream

import java.io.File

import java.io.BufferedInputStream

import java.io.FileInputStream
import kotlin.collections.ArrayList


private val songName = mutableStateOf("")
private val fileName = mutableStateOf("")
private var chunks: File = File("")

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
    val uploadSongViewModel: UploadSongViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    Image(
        painter = painterResource(id = logo),
        contentDescription = "logo",
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
    )
    
    TextField(
        input = songName, 
        text = stringResource(id = R.string.song_name),
        visibility = true
    )

    DropDownAlbumMenu()

    PickAudioRow()

    MediumRoundButton(text = stringResource(id = R.string.save), onClick = {
        val id = loginViewModel.sharedPreferences.getInt("id", -1)

        val files = splitFile(chunks)
        val songID = System.currentTimeMillis().toString() + id

        files.forEachIndexed { index, file ->
            val uploadSongRequest = UploadSongRequest(id, songName.value, "test", files.size, index, songID, 1)

            uploadSongViewModel.uploadSong(
                token,
                uploadSongRequest,
                MultipartBody.Part.createFormData("file", songName.value, RequestBody.create("audio/*".toMediaTypeOrNull(), file))
            )

//            file.delete()
        }
    })
}

@Composable
private fun DropDownAlbumMenu(){
    var expanded by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf("Single") }
    val albumList = listOf(
        "Single",
        "Album names"
    )

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
            text = selectedAlbum,
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
                .background(colorResource(id = R.color.darker_gray))
        ) {
            albumList.forEach { album ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        selectedAlbum = album
                    }
                ) {
                    Text(
                        text = album,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun PickAudioRow(){
    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
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
