package hc.candlecraft.candlecraftultimate.common

import androidx.compose.ui.graphics.Color
import hc.candlecraft.candlecraftultimate.ui.theme.*

sealed class CardColor(val id: Int, val cardColor: Color) {
    object LightBlue : CardColor(id = 1, cardColor = BackgroundColorLightBlue)
    object LightRed : CardColor(id = 2, cardColor = BackgroundColorLightRed)
    object LightGreen : CardColor(id = 3, cardColor = BackgroundColorLightGreen)
    object LightPink : CardColor(id = 4, cardColor = BackgroundColorLightPink)

    object DarkBlue : CardColor(id = 1, cardColor = BackgroundColorDarkBlue)
    object DarkRed : CardColor(id = 2, cardColor = BackgroundColorDarkRed)
    object DarkGreen : CardColor(id = 3, cardColor = BackgroundColorDarkGreen)
    object DarkPink : CardColor(id = 4, cardColor = BackgroundColorDarkPink)
}
