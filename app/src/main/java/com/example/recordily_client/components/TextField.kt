package com.example.recordily_client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource

@Composable
fun TextField(input: MutableState<String>, text: String) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .clip(MaterialTheme.shapes.small),
        value = input.value,
        label = { Text(text) },
        onValueChange = { input.value = it },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = colorResource(R.color.darker_gray),
            cursorColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            )
    )
}