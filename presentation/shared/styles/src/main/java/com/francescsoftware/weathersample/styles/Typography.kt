package com.francescsoftware.weathersample.styles

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Montserrat = FontFamily(
    Font(com.francescsoftware.weathersample.shared.assets.R.font.montserrat_regular, FontWeight.Normal),
    Font(com.francescsoftware.weathersample.shared.assets.R.font.montserrat_medium, FontWeight.Medium),
    Font(com.francescsoftware.weathersample.shared.assets.R.font.montserrat_bold, FontWeight.Bold),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Montserrat,
        fontSize = 96.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 117.sp,
        letterSpacing = (-1.5).sp
    ),
    displayMedium = TextStyle(
        fontFamily = Montserrat,
        fontSize = 46.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 56.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = Montserrat,
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Montserrat,
        fontSize = 60.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 73.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontSize = 48.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 59.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = Montserrat,
        fontSize = 30.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 29.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 17.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 1.25.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat,
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 14.sp,
        letterSpacing = 1.sp
    ),
)
