package com.example.recordily_client.pages.common

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recordily_client.R
import com.example.recordily_client.components.CircularProgressBar
import com.example.recordily_client.components.RoundButton
import com.example.recordily_client.components.SimpleCircularProgressBar
import com.example.recordily_client.validation.isValidEmail
import kotlinx.coroutines.launch
import com.example.recordily_client.components.TextField
import com.example.recordily_client.view_models.ForgotPasswordViewModel

private val errorMessage = mutableStateOf("")
private val visible = mutableStateOf(false)
private var progressVisibility = mutableStateOf(false)

@ExperimentalAnimationApi
@Composable
fun ResetPasswordPage() {
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
            BoxContent()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            errorMessage.value = ""
            visible.value = false
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun BoxContent() {
    val image = if (isSystemInDarkTheme())
        R.drawable.recordily_dark_logo
        else R.drawable.recordily_white_logo
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
            text = stringResource(R.string.reset_password),
            color = MaterialTheme.colors.onPrimary,
            fontSize = dimensionResource(R.dimen.font_title).value.sp,
            fontWeight = FontWeight.ExtraBold
        )

        TextFieldColumn()

        AnimatedVisibility(
            visible = visible.value,
            enter = slideInHorizontally(
                initialOffsetX = { -40 }
            ),
            exit = slideOutHorizontally()
        ) {
            Text(
                text = errorMessage.value,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@SuppressLint("CommitPrefEdits")
@Composable
private fun TextFieldColumn() {
    val email = remember { mutableStateOf("") }
    val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
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

        if(!progressVisibility.value) {
            RoundButton(text = stringResource(R.string.submit), onClick = {
                progressVisibility.value = true
                visible.value = false

                if (!isValidEmail(email.value)) {
                    errorMessage.value = "Invalid Email"
                    visible.value = true
                    progressVisibility.value = false
                    return@RoundButton
                }

                coroutineScope.launch {
                    val emailSent = forgotPasswordViewModel.resetPassword(email.value)

                    if (!emailSent) {
                        errorMessage.value = "Invalid Email"
                        visible.value = true
                        progressVisibility.value = false
                        return@launch
                    }

                    errorMessage.value = "Email Sent"
                    visible.value = true
                    progressVisibility.value = false
                }

            })
        } else {
            SimpleCircularProgressBar()
        }
    }
}


