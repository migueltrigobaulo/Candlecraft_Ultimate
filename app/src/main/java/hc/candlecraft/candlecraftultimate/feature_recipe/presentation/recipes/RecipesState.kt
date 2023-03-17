package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.OrderType
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.RecipeOrder

data class RecipesState(
    val recipes: List<Recipe> = emptyList(),
    val noRecipesInDB: Boolean = false,
    val recipeOrder: RecipeOrder = RecipeOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
