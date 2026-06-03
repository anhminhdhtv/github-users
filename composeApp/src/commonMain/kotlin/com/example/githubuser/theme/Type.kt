package com.example.githubuser.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import githubusers.composeapp.generated.resources.Res
import githubusers.composeapp.generated.resources.outfit_bold
import githubusers.composeapp.generated.resources.outfit_medium
import githubusers.composeapp.generated.resources.outfit_regular
import githubusers.composeapp.generated.resources.outfit_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun getAppTypography(): Typography {
    val outfitFontFamily = FontFamily(
        Font(Res.font.outfit_regular, FontWeight.Normal),
        Font(Res.font.outfit_medium, FontWeight.Medium),
        Font(Res.font.outfit_semibold, FontWeight.SemiBold),
        Font(Res.font.outfit_bold, FontWeight.Bold),
    )

    val defaultTypography = Typography()

    return Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = outfitFontFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = outfitFontFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = outfitFontFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = outfitFontFamily),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = outfitFontFamily),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = outfitFontFamily),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = outfitFontFamily),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = outfitFontFamily),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = outfitFontFamily),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = outfitFontFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = outfitFontFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = outfitFontFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = outfitFontFamily),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = outfitFontFamily),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = outfitFontFamily),
    )
}
