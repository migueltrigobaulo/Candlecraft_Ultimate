package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.IngredientRepository

class DeleteIngredient(
    private val repository: IngredientRepository
) {
    suspend operator fun invoke(
        ingredient: Ingredient
    ) {
        repository.deleteIngredient(ingredient)
    }
}