package com.example.recordily_client.pages.common

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*

private val name = mutableStateOf("")
private val bio = mutableStateOf("")

@Composable
fun EditProfilePage(navController: NavController) {
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
                    EditProfileContent()
                }
            }
        }
    )
}

@Composable
private fun EditProfileContent(){

    Image(
        painter = painterResource(id = R.drawable.profile_picture),
        contentDescription = "logo",
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
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

    MediumRoundButton(text = stringResource(id = R.string.save), onClick = {})
}
