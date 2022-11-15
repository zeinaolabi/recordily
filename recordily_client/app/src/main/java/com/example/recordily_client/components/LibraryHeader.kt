package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.ProfileViewModel

@Composable
fun LibraryHeader(input: MutableState<String>, navController: NavController) {
    val profileViewModel: ProfileViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()

    val token = "Bearer " + loginViewModel.sharedPreferences.getString("token", "").toString()

    profileViewModel.getInfo(token)

    val picture = profileViewModel.userInfoResultLiveData.observeAsState().value?.profile_picture

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_medium)
            )
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth(.80f)
                .clip(MaterialTheme.shapes.small),
            value = input.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    fontSize = dimensionResource(id = R.dimen.font_medium).value.sp
                )
                          },
            onValueChange = { input.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.darker_gray),
                cursorColor = Color.White,
                textColor = Color.White,
                unfocusedIndicatorColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = MaterialTheme.colors.surface,
            )
        )

        Image(
            painter =
            if(picture != null && picture != ""){
                rememberAsyncImagePainter(picture)
            }
            else {
                painterResource(R.drawable.profile_picture)
            },
            contentDescription = "profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
                .clickable {
                    navigateTo(
                        navController = navController,
                        destination = Screen.ProfilePage.route,
                        popUpTo = Screen.LandingPage.route
                    )
                }
        )
    }
}