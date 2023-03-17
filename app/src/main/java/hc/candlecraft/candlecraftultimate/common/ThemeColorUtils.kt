package hc.candlecraft.candlecraftultimate.common

import androidx.compose.ui.graphics.Color
import hc.candlecraft.candlecraftultimate.ui.theme.cardColorSchemeThemed

object ThemeColorUtils {
    fun getColorById(colorId: Int): Color {
        return when (colorId) {
            cardColorSchemeThemed.red.id -> cardColorSchemeThemed.red.cardColor
            cardColorSchemeThemed.green.id -> cardColorSchemeThemed.green.cardColor
            cardColorSchemeThemed.pink.id -> cardColorSchemeThemed.pink.cardColor
            else -> cardColorSchemeThemed.blue.cardColor
        }
    }
}
