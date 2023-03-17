package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.IngredientRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.OrderType
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.RecipeOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetIngredients(
    private val repository: IngredientRepository
) {
    operator fun invoke(
        recipeId: Int
    ): Flow<List<Ingredient>> {
        return repository.getAllIngredientsByRecipeId(recipeId).map { ingredients ->
            ingredients.sortedBy { it.id }
        }
    }
}