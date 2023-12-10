package com.example.skan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val ColorPalette = darkColors(
    primary = GreenBackground,
    primaryVariant = Purple700,
    secondary = Gray200,
    onSecondary = Gray300,
    background = GreenBackground,
    onPrimary = GreenMenu
)

private val LightColorPalette = lightColors(
    primary = GreenBackground,
    primaryVariant = Purple700,
    secondary = Gray200,
    background = GreenBackground
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    30B0C7
    5EC6F5
    5EF5D0
    535353
        6F767E
    636C72
    TODO: Define colors for dark mode. switch the colors at the end
    */
)

@Composable
fun SkanTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = ColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}