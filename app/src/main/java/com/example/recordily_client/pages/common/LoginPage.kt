package com.example.recordily_client.pages.common

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.RoundButton
import com.example.recordily_client.navigation.Screen
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.validation.isValidEmail
import com.example.recordily_client.validation.isValidPassword
import com.example.recordily_client.view_models.LoginViewModel
import kotlinx.coroutines.launch

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun LoginPage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight(.75f)
                .fillMaxWidth(.9f)
                .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.onSurface)
                .align(Alignment.Center)
                .padding(dimensionResource(R.dimen.padding_large))
                .zIndex(1f),
        ) {
            BoxContent(navController)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun BoxContent(navController: NavController) {
    val image = if (isSystemInDarkTheme()) R.drawable.recordily_dark_logo else R.drawable.recordily_light_logo
    val logo: Painter = painterResource(id = image)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = logo,
            contentDescription = "",
            modifier = Modifier
                .width(165.dp)
                .height(100.dp)
        )

        Text(
            text = stringResource(R.string.login),
            color = MaterialTheme.colors.onPrimary,
            fontSize = dimensionResource(R.dimen.font_title).value.sp,
            fontWeight = FontWeight.ExtraBold
        )

        TextFieldColumn(navController)

        CreateAccountRow(navController)

        AnimatedVisibility(
            visible = visible.value,
            enter = expandVertically(
                expandFrom = Alignment.Top
            ),
            exit = fadeOut()
        ) {
            Text(
                text = errorMessage.value,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,

            )
        }

    }
}

@Composable
fun TextFieldColumn(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val userViewModel : LoginViewModel = viewModel()
//    val loginResponse = userViewModel.loginResultLiveData.observeAsState()
//    if (loginResponse.value?.token !== "") {
//        navController.navigate(Screen.CommonLandingPage.route)
//    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            bottom = dimensionResource(R.dimen.padding_large),
            top = dimensionResource(R.dimen.padding_large)
        )
    ) {

        com.example.recordily_client.TextField(
            input = email,
            text = stringResource(R.string.email),
            visibility = true
        )

        com.example.recordily_client.TextField(
            input = password,
            text = stringResource(R.string.password),
            visibility = false
        )

        Text(text = stringResource(R.string.forgot_password),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.End,
            fontSize = dimensionResource(R.dimen.font_small).value.sp,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_very_small))
                .align(Alignment.End)
                .clickable { })

        RoundButton(text = stringResource(R.string.login), onClick = {

            if(!isValidEmail(email.value) || !isValidPassword(password.value)){
                errorMessage.value = "Invalid Email or Password"
                visible.value = true
                return@RoundButton
            }

            coroutineScope.launch {
                val request = LoginRequest(
                    email.value.lowercase().trim(),
                    password.value
                )

                if (!userViewModel.login(request)){
                    errorMessage.value = "Invalid Email or Password"
                    visible.value = true
                    return@launch
                }
                navController.navigate(Screen.CommonLandingPage.route)
            }

        })
    }
}

@Composable
fun CreateAccountRow(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.create_account) + " ",
            color = Color.White,
            fontSize = dimensionResource(R.dimen.font_small).value.sp
        )

        Text(
            text = stringResource(R.string.signup),
            color = MaterialTheme.colors.primary,
            fontSize = dimensionResource(R.dimen.font_small).value.sp,
            modifier = Modifier.clickable {
                navController.navigate(Screen.RegistrationPage.route)
            }
        )
    }
}
