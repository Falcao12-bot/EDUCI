package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EduCiDarkPrimary,
    onPrimary = Color(0xFF003822),
    primaryContainer = EduCiDarkPrimaryContainer,
    onPrimaryContainer = Color(0xFFA7F3D0),
    secondary = EduCiOrangeAccent,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF431407),
    onSecondaryContainer = Color(0xFFFFDBCF),
    tertiary = EduCiGoldXp,
    background = EduCiDarkBackground,
    surface = EduCiDarkSurface,
    surfaceVariant = EduCiDarkSurfaceVariant,
    onBackground = EduCiDarkTextPrimary,
    onSurface = EduCiDarkTextPrimary,
    outline = EduCiDarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = EduCiGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = EduCiGreenContainer,
    onPrimaryContainer = EduCiGreenOnContainer,
    secondary = EduCiOrangeAccent,
    onSecondary = Color.White,
    secondaryContainer = EduCiOrangeLight,
    onSecondaryContainer = EduCiOrangeDark,
    tertiary = EduCiGoldXp,
    background = EduCiBackground, // Vert-blanc
    surface = EduCiSurface,       // Blanc
    surfaceVariant = EduCiSurfaceVariant,
    onBackground = EduCiTextPrimary,
    onSurface = EduCiTextPrimary,
    outline = EduCiBorder
)

@Composable
fun EduCiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    EduCiTheme(darkTheme = darkTheme, content = content)
}
