package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.RecipeOrder

sealed class RecipesEvent {
    data class Order(val recipeOrder: RecipeOrder): RecipesEvent()
    data class SearchByName(val recipeName: String): RecipesEvent()
    data class DeleteRecipe(val recipe: Recipe): RecipesEvent()
    object RestoreRecipe: RecipesEvent()
    object TogleOrderSection: RecipesEvent()
}
