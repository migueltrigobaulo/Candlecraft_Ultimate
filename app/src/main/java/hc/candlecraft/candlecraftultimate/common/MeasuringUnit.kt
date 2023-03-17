package hc.candlecraft.candlecraftultimate.common

import hc.candlecraft.candlecraftultimate.R

sealed class MeasuringUnit(val id: Int, val name: Int, val abbreviatedName: Int) {
    object Grams : MeasuringUnit(id = 1, name = R.string.grams, abbreviatedName = R.string.grams_abbreviated)
    object Ounces : MeasuringUnit(id = 2, name = R.string.ounces, abbreviatedName = R.string.ounces_abbreviated)
    object Percentage : MeasuringUnit(id = 3, name = R.string.percentage, abbreviatedName = R.string.percentage_abbreviated)
}
