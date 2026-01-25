package com.example.imirly.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Dark mode
private val DarkColorScheme = darkColorScheme(
    primary = iMirlyPrimary,
    onPrimary = iMirlyOnPrimary,

    background = iMirlyPrimaryDark,
    onBackground = iMirlyOnPrimary,

    surface = iMirlyPrimaryDark,
    onSurface = iMirlyOnPrimary,

    secondary = iMirlyPrimaryLight
)

//  Light mode
private val LightColorScheme = lightColorScheme(
    primary = iMirlyPrimary,
    onPrimary = iMirlyOnPrimary,

    background = iMirlyBackground,
    onBackground = iMirlyOnBackground,

    surface = Color.White,
    onSurface = iMirlyOnBackground,

    secondary = iMirlyPrimaryLight,
    onSecondary = iMirlyPrimaryDark
)

@Composable
fun ImirlyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
