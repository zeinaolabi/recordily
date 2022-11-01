package com.example.recordily_client.pages.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.*
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.navigation.navigateTo
import com.example.recordily_client.view_models.LoginViewModel

@Composable
fun SettingsPage(navController: NavController) {
    Scaffold(
        topBar = { ExitBar(navController, stringResource(id = R.string.settings)) },
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
                    SettingsContent(navController)
                }
            }
        }
    )
}

@Composable
private fun SettingsContent(navController: NavController){
    val loginViewModel : LoginViewModel = viewModel()

    Text(
        text = stringResource(id = R.string.edit_profile),
        fontSize = dimensionResource(id = R.dimen.font_title).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary
    )

    Text(
        text = stringResource(id = R.string.logout),
        fontSize = dimensionResource(id = R.dimen.font_title).value.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.clickable{
            loginViewModel.logout()
            navigateTo(
                navController = navController,
                destination = Screen.LoginPage.route,
                popUpTo = Screen.LoginPage.route
            )
        }
    )

}
