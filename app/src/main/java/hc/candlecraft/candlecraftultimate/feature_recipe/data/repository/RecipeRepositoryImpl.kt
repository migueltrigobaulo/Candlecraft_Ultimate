package hc.candlecraft.candlecraftultimate.feature_recipe.data.repository

import hc.candlecraft.candlecraftultimate.feature_recipe.data.data_source.RecipeDao
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val dao: RecipeDao
): RecipeRepository {
    override fun getRecipes(): Flow<List<Recipe>> {
        return dao.getAllRecipes()
    }

    override suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getRecipeById(id)
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        dao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        dao.deleteRecipe(recipe)
    }
}