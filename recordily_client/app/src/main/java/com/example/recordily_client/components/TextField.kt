package com.example.recordily_client.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.recordily_client.R

@Composable
fun TextField(input: MutableState<String>, text: String, visibility: Boolean) {

    TextField(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .clip(MaterialTheme.shapes.small),
        value = input.value,
        label = {
            Text(
                text = text,
                fontWeight = FontWeight.Medium,
                fontSize = dimensionResource(id = R.dimen.font_small).value.sp
            )
                },
        onValueChange = { input.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = colorResource(R.color.darker_gray),
            cursorColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colors.secondary,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            disabledLabelColor = MaterialTheme.colors.secondary
            ),
        visualTransformation =
        if (visibility) VisualTransformation.None
        else PasswordVisualTransformation()
    )

}

@Composable
fun SearchTextField(input: MutableState<String>) {
    val grayColor = Color(0xFF828282)

    TextField(
        modifier = Modifier
            .fillMaxWidth()
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
            backgroundColor = grayColor,
            cursorColor = Color.White,
            textColor = Color.White,
            unfocusedIndicatorColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = MaterialTheme.colors.surface,
        )
    )

}

