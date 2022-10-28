package com.example.recordily_client.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryColor,
    primaryVariant = primaryLighter,
    secondary = secondaryColor,
    surface = surfaceColor,
    background = backgroundColor,
    onSurface = Color.Black,
    onPrimary = Color.White,

    )

private val LightColorPalette = lightColors(
    primary = primaryColor,
    primaryVariant = primaryLighter,
    secondary = secondaryColor,
    surface = surfaceColor,
    background = lightBackground,
    onSurface = Color.White,
    onPrimary = Color.Black,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    */
)

@Composable
fun Recordily_clientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}