package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.InvalidRecipeNameException
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository

class InsertRecipe(
    private val repository: RecipeRepository
) {

    @Throws(InvalidRecipeNameException::class)
    suspend operator fun invoke(
        recipe: Recipe
    ) {
        if (recipe.name.isBlank()) {
            throw InvalidRecipeNameException()
        }

        repository.insertRecipe(recipe)
    }
}