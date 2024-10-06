package com.example.recipeapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Earthy tones
val EarthyGreen = Color(0xFF7B8D42)
val SoftBrown = Color(0xFF8C6E58)
val PaleCream = Color(0xFFF5E9DC)
val SoftOrange = Color(0xFFF4A261)
val DeepBrown = Color(0xFF3E2723)



private val DarkColorScheme = darkColorScheme(
    primary = EarthyGreen,
    secondary = SoftBrown,
    tertiary = SoftOrange,
    background = DeepBrown,
    surface = DeepBrown,
    onPrimary = PaleCream,
    onSecondary = PaleCream,
    onTertiary = PaleCream,
    onBackground = PaleCream,
    onSurface = PaleCream,
    secondaryContainer = SoftBrown,
    onSecondaryContainer = PaleCream
)

private val LightColorScheme = lightColorScheme(
    primary = EarthyGreen,
    secondary = SoftBrown,
    tertiary = SoftOrange,
    background = PaleCream,
    surface = PaleCream,
    onPrimary = DeepBrown,
    onSecondary = DeepBrown,
    onTertiary = DeepBrown,
    onBackground = DeepBrown,
    onSurface = DeepBrown,
    secondaryContainer = SoftBrown,
    onSecondaryContainer = DeepBrown
)

@Composable
fun RecipeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}