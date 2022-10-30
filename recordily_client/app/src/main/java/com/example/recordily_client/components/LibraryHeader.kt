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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.navigation.Screen

@Composable
fun LibraryHeader(input: MutableState<String>, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
    ){
        TextField(
            modifier = Modifier
                .fillMaxWidth(.85f)
                .clip(MaterialTheme.shapes.small),
            value = input.value,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                )
            },
            placeholder = { Text(text = stringResource(id = R.string.search)) },
            onValueChange = { input.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                cursorColor = Color.White,
                textColor = Color.White,
                unfocusedIndicatorColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = MaterialTheme.colors.surface,
            )
        )

        Image(
            painter = painterResource(R.drawable.recordily_gray_logo),
            contentDescription = "profile picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
                .clickable {
                    navController.navigate(Screen.CommonProfilePage.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
        )
    }
}