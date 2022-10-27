package com.example.recordily_client

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.recordily_client.viewModels.UserViewModel
import com.example.recordily_client.components.RoundButton
import com.example.recordily_client.requests.LoginRequest


//private val viewModel: UserViewModel by lazy {
//    ViewModelProvider(this)[UserViewModel::class.java]
//}

private var errorMessage = mutableStateOf("")

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
                .fillMaxHeight(.85f)
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

@Composable
fun RegistrationContent(navController: NavController) {
    val image =
        if (isSystemInDarkTheme()) R.drawable.recordily_dark_logo else R.drawable.recordily_light_logo
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

        RegistrationColumn()

        SignInRow(navController)

        Text(
            text = errorMessage.value,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun RegistrationColumn() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

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
//            handleLogin(LoginRequest(email.value, password.value))
        })
    }
}

@Composable
fun SignInRow(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.sign_in) + " ",
            color = Color.White,
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
fun UserTypesRow() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_small)
            )
    ){
        Text(
            text = stringResource(R.string.signupas),
            color = Color.White
        )

        RadioButtons()
    }
}

@Composable
fun RadioButtons(){
    val radioOptions = listOf(stringResource(R.string.artist), stringResource(R.string.listener))
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }

    radioOptions.forEach { text ->
        Row(
            Modifier.selectable(
                selected = (text == selectedOption),
                onClick = {
                    onOptionSelected(text)
                }
            )
        ) {
            RadioButton(
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colors.secondary,
                    unselectedColor = Color.White,
                    disabledColor = Color.LightGray
                )
            )
            Text(
                text = text,
                color = Color.White
            )
        }
    }
}

//private fun handleLogin(loginRequest: LoginRequest) {
//    loginRequest.email = loginRequest.email.lowercase().trim()
//    loginRequest.password = loginRequest.password.trim()
//
//    viewModel.login(loginRequest)
//    viewModel.loginLiveData.observe(this) { response ->
//        if (response == null) {
//            Toast.makeText(
//                this@LoginActivity,
//                "Unsuccessful Network Call!",
//                Toast.LENGTH_SHORT
//            ).show()
//            return@observe
//        }
//
//
//    }
//}