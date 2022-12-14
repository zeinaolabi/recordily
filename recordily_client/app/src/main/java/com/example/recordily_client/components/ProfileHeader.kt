package com.example.recordily_client.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.responses.UserResponse
import com.example.recordily_client.validation.UserCredentials

@Composable
fun ProfileHeader(navController: NavController, profile: UserResponse){
    Row(
        modifier = Modifier.padding(
            vertical = dimensionResource(id = R.dimen.padding_large),
            horizontal = dimensionResource(id = R.dimen.padding_medium)
        )
    ){
        Image(
            painter =
            if(profile.profile_picture != null && profile.profile_picture != ""){
                rememberAsyncImagePainter(profile.profile_picture)
            }
            else{
                painterResource(id = R.drawable.profile_picture)
            },
            contentDescription = "profile picture",
            modifier = Modifier
                .size(125.dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colors.secondary, CircleShape),
            contentScale = ContentScale.Crop
        )

        ProfileInfo(navController, profile)
    }
}

@Composable
private fun ProfileInfo(navController: NavController, profile: UserResponse){
    val userCredentials: UserCredentials = viewModel()
    
    Column(
        modifier = Modifier
            .height(125.dp)
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column{
            Text(
                text = profile.name ?: "User Name",
                fontSize = dimensionResource(id = R.dimen.font_medium).value.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium)),
                color = MaterialTheme.colors.onPrimary
            )

            Text(
                text = profile.biography ?: "Bio",
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(
                imageVector =Icons.Default.Person,
                contentDescription = "profile",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .size(25.dp)
                    .clickable {
                        navigateTo(
                            navController = navController,
                            destination = Screen.EditProfilePage.route,
                            popUpTo = Screen.ProfilePage.route
                        )
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "logout",
                tint = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        userCredentials.removeCredentials()

                        navController.popBackStack(0, true)
                        navigateTo(
                            navController = navController,
                            destination = Screen.LoginPage.route,
                            popUpTo = Screen.LoginPage.route
                        )
                }
            )
        }
    }
}