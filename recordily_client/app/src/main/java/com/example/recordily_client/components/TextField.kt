package com.example.recordily_client

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

@Composable
fun SearchTextField(input: MutableState<String>) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .padding(dimensionResource(id = R.dimen.padding_medium)),
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
            backgroundColor = MaterialTheme.colors.surface,
            cursorColor = Color.White,
            textColor = Color.White,
            unfocusedIndicatorColor = MaterialTheme.colors.surface,
            focusedIndicatorColor = MaterialTheme.colors.surface,
        )
    )

}