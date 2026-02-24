package com.wangxingxing.wxxcomposetemplate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * 根据主题色索引获取颜色
 */
fun getThemeColor(colorIndex: Int): Color {
    return when (colorIndex) {
        0 -> Color(0xFF6650a4) // 默认紫色
        1 -> Color(0xFF1976D2) // 蓝色
        2 -> Color(0xFF388E3C) // 绿色
        3 -> Color(0xFFF57C00) // 橙色
        4 -> Color(0xFFD32F2F) // 红色
        5 -> Color(0xFF7B1FA2) // 深紫色
        6 -> Color(0xFF0288D1) // 浅蓝色
        7 -> Color(0xFF5D4037) // 棕色
        else -> Color(0xFF6650a4) // 默认紫色
    }
}

/**
 * 根据主题色生成深色和浅色方案
 */
fun getColorScheme(themeColor: Color, darkTheme: Boolean): androidx.compose.material3.ColorScheme {
    val primary = themeColor
    val onPrimary = Color.White
    
    // 根据主题色生成背景和表面颜色，使其与主题色协调
    val background = if (darkTheme) {
        // 深色主题：使用主题色的深色变体作为背景
        // 将主题色与深色背景混合，产生协调的深色背景
        val darkBase = Color(0xFF121212)
        Color(
            red = (darkBase.red * 0.7f + themeColor.red * 0.3f).coerceIn(0f, 1f),
            green = (darkBase.green * 0.7f + themeColor.green * 0.3f).coerceIn(0f, 1f),
            blue = (darkBase.blue * 0.7f + themeColor.blue * 0.3f).coerceIn(0f, 1f),
            alpha = 1f
        )
    } else {
        // 浅色主题：使用主题色的极浅色变体作为背景
        // 将主题色与浅色背景混合，产生协调的浅色背景
        val lightBase = Color(0xFFFFFBFE)
        Color(
            red = (lightBase.red * 0.95f + themeColor.red * 0.05f).coerceIn(0f, 1f),
            green = (lightBase.green * 0.95f + themeColor.green * 0.05f).coerceIn(0f, 1f),
            blue = (lightBase.blue * 0.95f + themeColor.blue * 0.05f).coerceIn(0f, 1f),
            alpha = 1f
        )
    }
    
    val surface = if (darkTheme) {
        // 深色主题：表面颜色稍亮于背景，也融入主题色
        Color(
            red = (background.red * 0.9f + themeColor.red * 0.1f).coerceIn(0f, 1f),
            green = (background.green * 0.9f + themeColor.green * 0.1f).coerceIn(0f, 1f),
            blue = (background.blue * 0.9f + themeColor.blue * 0.1f).coerceIn(0f, 1f),
            alpha = 1f
        )
    } else {
        // 浅色主题：表面颜色为白色或极浅色，融入主题色
        Color(
            red = (background.red * 0.98f + themeColor.red * 0.02f).coerceIn(0f, 1f),
            green = (background.green * 0.98f + themeColor.green * 0.02f).coerceIn(0f, 1f),
            blue = (background.blue * 0.98f + themeColor.blue * 0.02f).coerceIn(0f, 1f),
            alpha = 1f
        )
    }
    
    val surfaceVariant = if (darkTheme) {
        // 深色主题：表面变体颜色，融入更多主题色
        Color(
            red = (background.red * 0.8f + themeColor.red * 0.2f).coerceIn(0f, 1f),
            green = (background.green * 0.8f + themeColor.green * 0.2f).coerceIn(0f, 1f),
            blue = (background.blue * 0.8f + themeColor.blue * 0.2f).coerceIn(0f, 1f),
            alpha = 1f
        )
    } else {
        // 浅色主题：表面变体颜色，融入主题色
        Color(
            red = (background.red * 0.92f + themeColor.red * 0.08f).coerceIn(0f, 1f),
            green = (background.green * 0.92f + themeColor.green * 0.08f).coerceIn(0f, 1f),
            blue = (background.blue * 0.92f + themeColor.blue * 0.08f).coerceIn(0f, 1f),
            alpha = 1f
        )
    }
    
    val primaryContainer = if (darkTheme) {
        themeColor.copy(alpha = 0.3f)
    } else {
        themeColor.copy(alpha = 0.1f)
    }
    
    val onPrimaryContainer = if (darkTheme) {
        themeColor.copy(
            red = (themeColor.red * 0.8f).coerceIn(0f, 1f),
            green = (themeColor.green * 0.8f).coerceIn(0f, 1f),
            blue = (themeColor.blue * 0.8f).coerceIn(0f, 1f)
        )
    } else {
        themeColor
    }
    
    val onBackground = if (darkTheme) Color.White else Color(0xFF1C1B1F)
    val onSurface = if (darkTheme) Color.White else Color(0xFF1C1B1F)
    val onSurfaceVariant = if (darkTheme) Color(0xFFCAC4D0) else Color(0xFF49454F)
    
    return if (darkTheme) {
        darkColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant
        )
    } else {
        lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer,
            background = background,
            onBackground = onBackground,
            surface = surface,
            onSurface = onSurface,
            surfaceVariant = surfaceVariant,
            onSurfaceVariant = onSurfaceVariant
        )
    }
}

enum class ThemeMode { SYSTEM, LIGHT, DARK }

fun resolveIsDark(mode: ThemeMode): Boolean {
    return when (mode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }
}

@Composable
fun AppTheme(
    mode: ThemeMode = ThemeMode.SYSTEM,
    dynamicColor: Boolean = true,
    themeColorIndex: Int = 0,
    content: @Composable () -> Unit
) {
    val isDarkTheme = resolveIsDark(mode)
    val themeColor = getThemeColor(themeColorIndex)

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> getColorScheme(themeColor, isDarkTheme)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun WXXComposeTemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    themeColorIndex: Int = 0,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> getColorScheme(getThemeColor(themeColorIndex), darkTheme)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}