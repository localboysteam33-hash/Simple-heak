package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val HackrifatDarkColorScheme =
  darkColorScheme(
    primary = NeonPink,
    secondary = NeonBlue,
    tertiary = NeonCyan,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = LightText,
    onSecondary = LightText,
    onBackground = LightText,
    onSurface = LightText,
  )

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = HackrifatDarkColorScheme,
    typography = Typography,
    content = content
  )
}
