package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.OrderType
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.RecipeOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecipes(
    private val repository: RecipeRepository
) {
    operator fun invoke(
        recipeOrder: RecipeOrder = RecipeOrder.Date(OrderType.Descending)
    ): Flow<List<Recipe>> {
        return repository.getRecipes().map { recipe ->
            when(recipeOrder.orderType){
                is OrderType.Ascending -> {
                    when(recipeOrder) {
                        is RecipeOrder.Name -> recipe.sortedBy { it.name.lowercase() }
                        is RecipeOrder.Date -> recipe.sortedBy { it.lastEditTimestamp }
                    }
                }
                is OrderType.Descending -> {
                    when(recipeOrder) {
                        is RecipeOrder.Name -> recipe.sortedByDescending { it.name.lowercase() }
                        is RecipeOrder.Date -> recipe.sortedByDescending { it.lastEditTimestamp }
                    }
                }
            }
        }
    }
}