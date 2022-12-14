package com.example.recordily_client.pages.common

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.recordily_client.validation.isValidEmail
import com.example.recordily_client.validation.isValidPassword
import kotlinx.coroutines.launch
import com.example.recordily_client.components.TextField
import com.example.recordily_client.requests.LoginRequest
import com.example.recordily_client.requests.RegistrationRequest
import com.example.recordily_client.view_models.LoginViewModel
import com.example.recordily_client.view_models.RegistrationViewModel

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)
private val userType = mutableStateOf("")
private val userTypeID = mutableStateOf(-1)

@ExperimentalAnimationApi
@Composable
fun RegistrationPage(navController: NavController) {
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
                .fillMaxHeight(.9f)
                .fillMaxWidth(.9f)
                .border(0.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.onSurface)
                .align(Alignment.Center)
                .padding(dimensionResource(R.dimen.padding_large))
                .zIndex(1f),
        ) {
            RegistrationContent(navController)
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun RegistrationContent(navController: NavController) {
    val image =
        if (isSystemInDarkTheme()) R.drawable.recordily_dark_logo else R.drawable.recordily_white_logo
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

        RegistrationColumn(navController)

        SignInRow(navController)

        AnimatedVisibility(
            visible = visible.value,
            enter = slideInHorizontally(
                initialOffsetX = { -40 }
            ),
            exit = slideOutHorizontally()
        ){
            Text(
                text = errorMessage.value,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun RegistrationColumn(navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val loginViewModel : LoginViewModel = viewModel()
    val registerViewModel : RegistrationViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            bottom = dimensionResource(R.dimen.padding_large),
            top = dimensionResource(R.dimen.padding_large)
        )
    ) {

        TextField(
            input = email,
            text = stringResource(R.string.email),
            visibility = true
        )

        TextField(
            input = password,
            text = stringResource(R.string.password),
            visibility = false
        )

        TextField(
            input = confirmPassword,
            text = stringResource(R.string.confirm_password),
            visibility = false
        )

        UserTypesRow()

        RoundButton(text = stringResource(R.string.signup), onClick = {

            if(!errorHandling(email.value, password.value, confirmPassword.value) || !setTypeId()){
                return@RoundButton
            }

            coroutineScope.launch {
                val request = RegistrationRequest(
                    email.value.lowercase().trim(),
                    password.value,
                    userTypeID.value
                )

                if (!registerViewModel.register(request)){
                    setErrorMessage("Email Used", true)
                    return@launch
                }
                else{
                    val loginRequest = LoginRequest(
                        email.value.lowercase().trim(),
                        password.value
                    )

                    if(loginViewModel.login(loginRequest)){
                        navController.navigate(Screen.LandingPage.route) {
                            popUpTo(0)
                        }

                        errorMessage.value = ""
                        visible.value = false
                    }
                }

            }
        })
    }
}

@Composable
private fun SignInRow(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.sign_in) + " ",
            color = MaterialTheme.colors.onPrimary,
            fontSize = dimensionResource(R.dimen.font_small).value.sp
        )

        Text(
            text = stringResource(R.string.signin),
            color = MaterialTheme.colors.primary,
            fontSize = dimensionResource(R.dimen.font_small).value.sp,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
    }
}

@Composable
private fun UserTypesRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_small)
            )
    ){
        Text(
            text = stringResource(R.string.signupas),
            color = MaterialTheme.colors.onPrimary
        )

        RadioButtons()
    }
}

@Composable
private fun RadioButtons(){
    val radioOptions = listOf(stringResource(R.string.artist), stringResource(R.string.listener))
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }

    radioOptions.forEach { text ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.selectable(
                selected = (text == selectedOption),
                onClick = {
                    onOptionSelected(text)
                }
            )
        ) {
            RadioButton(
                selected = (text == selectedOption),
                onClick = {
                    onOptionSelected(text)
                    userType.value = text
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.secondary,
                    unselectedColor = MaterialTheme.colors.onPrimary
                )
            )
            Text(
                text = text,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

private fun errorHandling(email: String, password: String, confirmPassword: String): Boolean{
    if(!isValidEmail(email)){
        setErrorMessage("Invalid Email", true)
        return false
    }
    else if(!isValidPassword(password) || !isValidPassword(confirmPassword)){
        setErrorMessage("Password should have minimum of 6 chars", true)
        return false
    }
    else if(password != confirmPassword){
        setErrorMessage("Passwords don't match", true)
        return false
    }

    setErrorMessage("", false)
    return true
}

private fun setTypeId(): Boolean{
    if(userType.value == "Artist"){
        userTypeID.value = 0
        setErrorMessage("", false)
        return true
    }
    else if(userType.value == "Listener"){
        userTypeID.value = 1
        setErrorMessage("", false)
        return true
    }

    setErrorMessage("Missing Field", true)
    return false
}

private fun setErrorMessage(message: String, visibility: Boolean){
    errorMessage.value = message
    visible.value = visibility
}