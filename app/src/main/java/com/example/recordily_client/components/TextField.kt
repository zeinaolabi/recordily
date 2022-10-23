package com.example.recordily_client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource

@Composable
fun TextField(value: MutableState<String>, text: String) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .clip(MaterialTheme.shapes.small)
            .background(colorResource(R.color.gray)),
        value = "",
        label = { Text(text , color = colorResource(R.color.darker_gray)) },
        onValueChange = { value.value = it }
    )
}