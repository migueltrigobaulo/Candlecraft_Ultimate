package hc.candlecraft.candlecraftultimate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hc.candlecraft.candlecraftultimate.R

// Set of Material typography styles to start with

val font = FontFamily(
    fonts = listOf(
        Font(R.font.merienda),
        Font(R.font.merienda, weight = FontWeight.Bold),
        Font(R.font.merienda, weight = FontWeight.Light),
        Font(R.font.merienda, weight = FontWeight.Thin),
        Font(R.font.merienda, weight = FontWeight.Normal, style = FontStyle.Italic)
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = font,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = font,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = font,
        fontSize = 12.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = font,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    ),
    labelLarge = TextStyle(
        fontFamily = font
    ),
    labelMedium = TextStyle(
        fontFamily = font
    ),
    labelSmall = TextStyle(
        fontFamily = font
    ),
    titleLarge = TextStyle(
        fontFamily = font
    ),
    titleMedium = TextStyle(
        fontFamily = font
    ),
    titleSmall = TextStyle(
        fontFamily = font
    ),
    displayLarge = TextStyle(
        fontFamily = font
    ),
    displayMedium = TextStyle(
        fontFamily = font
    ),
    displaySmall = TextStyle(
        fontFamily = font
    ),
    headlineLarge = TextStyle(
        fontFamily = font
    ),
    headlineSmall = TextStyle(
        fontFamily = font
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)