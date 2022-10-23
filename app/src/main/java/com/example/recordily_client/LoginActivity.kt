package com.example.recordily_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import com.example.recordily_client.ui.theme.Recordily_clientTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recordily_client.components.RoundButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Recordily_clientTheme {
                LoginPage()
            }
        }
    }
}

@Composable
fun LoginPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight(.65f)
                .fillMaxWidth(.9f)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.onSurface)
                .align(Alignment.Center)
                .padding(dimensionResource(R.dimen.padding_large))
        ){
            BoxContent()
        }
    }
}

@Composable
fun BoxContent(){
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
                .height(100.dp))

        Text(
            text=stringResource(R.string.login),
            color=MaterialTheme.colors.onPrimary,
            fontSize = dimensionResource(R.dimen.font_title).value.sp,
            fontWeight = FontWeight.ExtraBold)

        TextFieldColumn()

        RoundButton(text = stringResource(R.string.login), onClick = (print("test")))

        CreateAccountRow()
    }
}

@Composable
fun TextFieldColumn() {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_large), top = dimensionResource(R.dimen.padding_large))
    ){
        TextField(email, stringResource(R.string.email))

        TextField(password, stringResource(R.string.password))

        Text(text = stringResource(R.string.forgot_password),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.End,
            fontSize = dimensionResource(R.dimen.font_small).value.sp,
            modifier = Modifier
                .padding(top = dimensionResource(R.dimen.padding_very_small))
                .align(Alignment.End)
                .clickable { /*TODO*/ })
    }
}

@Composable
fun CreateAccountRow() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ){
        Text(
            text = stringResource(R.string.create_account) + " ",
            color = MaterialTheme.colors.onPrimary,
            fontSize = dimensionResource(R.dimen.font_small).value.sp
        )

        Text(
            text = stringResource(R.string.signup),
            color = MaterialTheme.colors.primary,
            fontSize = dimensionResource(R.dimen.font_small).value.sp,
            modifier = Modifier.clickable { /*TODO*/ }
        )
    }
}

