package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.OrderType
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.RecipeOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecipe(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(
        recipeId: Int
    ): Recipe? {
        return repository.getRecipeById(recipeId)
    }
}