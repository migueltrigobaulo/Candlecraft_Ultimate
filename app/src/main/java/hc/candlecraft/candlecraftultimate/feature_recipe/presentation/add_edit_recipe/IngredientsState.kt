package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient

data class IngredientsState(
    val ingredients: ArrayList<Ingredient> = arrayListOf(),
    val noIngredientsInDB: Boolean = false
)