package com.verbum.verbum.frontend.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

//Configuration du thème de l'application
private val ColorScheme = darkColorScheme(
    primary = Color(0xFF689F38),
    secondary = Color(0xFFFFA000),
    tertiary = Color(0xFF0288D1),
    onBackground = Color(0xFFD1D1D1),
    onPrimary = Color(0xFF2A2A2A),
)


val ColorScheme.correctBackground @Composable get() = Color(0xFF4CAF50)
val ColorScheme.wrongPositionBackground @Composable get() = Color(0xFFFFA000)
val ColorScheme.incorrectBackground @Composable get() = Color(0xFF5B5B5B)
val ColorScheme.enteringBackground @Composable get() = MaterialTheme.colorScheme.background
val ColorScheme.keyboard @Composable get() = Color(0xFF393939)
val ColorScheme.keyboardDisabled @Composable get() = Color(0xFF642424)
val ColorScheme.onKeyboard @Composable get() = Color(0xFFE7E7E7)

@Composable
fun verbumTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = ColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}