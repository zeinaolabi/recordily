package com.example.recordily_client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextField(input: MutableState<String>, text: String, visibility: Boolean) {

    TextField(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .clip(MaterialTheme.shapes.small),
        value = input.value,
        label = { Text(text) },
        onValueChange = { input.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = colorResource(R.color.darker_gray),
            cursorColor = MaterialTheme.colors.secondary,
            textColor = Color.White,
            unfocusedIndicatorColor = MaterialTheme.colors.secondary,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            disabledLabelColor = MaterialTheme.colors.secondary
            ),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )

}