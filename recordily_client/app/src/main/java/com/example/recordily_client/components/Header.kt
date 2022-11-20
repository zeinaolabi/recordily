package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.validation.UserCredentials
import com.example.recordily_client.view_models.ProfileViewModel

@Composable
fun Header(navController: NavController){
    val logo =
        if (isSystemInDarkTheme()) R.drawable.recordily_gray_logo
        else R.drawable.recordily_light_mode
    val profileViewModel: ProfileViewModel = viewModel()
    val userCredentials: UserCredentials = viewModel()
    val token = userCredentials.getToken()

    profileViewModel.getInfo(token)
    val picture = profileViewModel.userInfoResultLiveData.observeAsState().value?.profile_picture

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.padding_medium),
                vertical = dimensionResource(R.dimen.padding_large)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(logo),
                contentDescription = "logo",
                modifier = Modifier
                    .height(64.dp)
                    .width(80.dp)
                    .clickable(
                        interactionSource = remember { NoRippleInteractionSource() },
                        indication = null
                    ) {
                        navigateTo(
                            navController = navController,
                            destination = Screen.LandingPage.route,
                            popUpTo = Screen.LandingPage.route
                        )
                    }
            )

            Text(
                text= stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.onPrimary,
                fontFamily = FontFamily.Cursive,
                fontSize = dimensionResource(R.dimen.font_large).value.sp
            )
        }

        Image(
            painter =
            if(picture !== null && picture != ""){
                rememberAsyncImagePainter(picture)
            }
            else {
                painterResource(R.drawable.profile_picture)
            },
            contentDescription = "profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
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