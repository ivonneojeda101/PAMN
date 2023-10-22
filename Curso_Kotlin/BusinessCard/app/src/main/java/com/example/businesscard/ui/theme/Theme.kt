package com.example.businesscard.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFA8D6B7),
    primaryVariant = Color(0xFF2A4475),
    secondaryVariant = Color(0xFF2D7543),
    secondary = Color(0xFF2FA),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFA8D6B7),
    primaryVariant = Color(0xFF2A4475),
    secondaryVariant = Color(0xFF2D7543),
    secondary = Color(0xFF2FA262),


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun BusinessCardTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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