package uz.coder.davomatapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = PrimaryColor,
            secondary = PrimaryColor,
            background = DarkBackground,
            surface = DarkBackground,
            onPrimary = OnPrimaryDark,
            onBackground = OnDarkBackground,
            onSurface = OnDarkBackground
        )
    } else {
        lightColorScheme(
            primary = PrimaryColor,
            secondary = PrimaryColor,
            background = LightBackground,
            surface = LightBackground,
            onPrimary = OnPrimaryLight,
            onBackground = OnLightBackground,
            onSurface = OnLightBackground
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}