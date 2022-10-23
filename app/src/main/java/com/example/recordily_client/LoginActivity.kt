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
            .padding(20.dp)
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

        Image(painter = logo,
            contentDescription = "",
            modifier = Modifier
            .width(165.dp)
            .height(100.dp))

        Text(text=stringResource(R.string.login),
            color=MaterialTheme.colors.onPrimary,
            fontSize = dimensionResource(R.dimen.title).value.sp,
            fontWeight = FontWeight.ExtraBold)

        RoundButton(stringResource(R.string.login), (print("test")))

    }
}


