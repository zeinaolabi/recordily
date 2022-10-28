package com.example.recordily_client.pages.artist

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.recordily_client.R
import com.example.recordily_client.components.MediumRoundButton
import com.example.recordily_client.components.innerShadow
import com.example.recordily_client.pages.common.BoxContent

@Composable
fun RecordPage(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium),
                    vertical = dimensionResource(id = R.dimen.padding_large)
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            ExitPage(navController)

            RecordContent()

            RecordButtonRow()
        }
    }
}

@Composable
fun ExitPage(navController: NavController){
    Row {
        Icon(
            Icons.Default.Close,
            contentDescription = "Exit",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )

        Text(
            text = stringResource(id = R.string.recording),
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun RecordContent(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "2:17",
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
            fontSize = dimensionResource(id = R.dimen.font_title).value.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colors.onPrimary
        )
        
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(CircleShape)
                .shadow(15.dp)
                .background(MaterialTheme.colors.primary)
                .innerShadow(
                    blur = 25.dp,
                    color = MaterialTheme.colors.primaryVariant,
                    cornersRadius = 150.dp,
                    offsetX = (-20.5).dp,
                    offsetY = (-15.5).dp
                )
                .clickable { },
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(id = R.drawable.record_logo),
                contentDescription = "Record Logo",
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colors.onPrimary
            )
        }


    }
}

@Composable
fun RecordButtonRow(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        MediumRoundButton(
            text = stringResource(id = R.string.save),
            onClick = {}
        )

        MediumRoundButton(
            text = stringResource(id = R.string.cancel),
            onClick = {}
        )
    }
}
