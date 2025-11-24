package com.wangxingxing.wxxcomposetemplate.ui.theme

import android.app.Activity
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
    val primaryContainer = if (darkTheme) themeColor.copy(alpha = 0.3f) else themeColor.copy(alpha = 0.1f)
    val onPrimaryContainer = if (darkTheme) themeColor else themeColor
    
    return if (darkTheme) {
        darkColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer
        )
    } else {
        lightColorScheme(
            primary = primary,
            onPrimary = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = onPrimaryContainer
        )
    }
}

@Composable
fun WXXComposeTemplateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // 禁用动态颜色，使用自定义主题色
    themeColorIndex: Int = 0, // 主题色索引，从外部传入
    content: @Composable () -> Unit
) {
    val themeColor = getThemeColor(themeColorIndex)
    
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> getColorScheme(themeColor, darkTheme)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}