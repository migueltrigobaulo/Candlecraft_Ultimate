package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.*
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.IngredientRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository

class InsertIngredient(
    private val repository: IngredientRepository
) {

    @Throws(InvalidRecipeNameException::class)
    suspend operator fun invoke(
        ingredient: Ingredient
    ) {
        if (ingredient.name.isBlank()) {
            throw InvalidIngredientNameException()
        }
        if (ingredient.amount <= 0) {
            throw InvalidIngredientAmountException()
        }

        repository.insertIngredient(ingredient)
    }
}