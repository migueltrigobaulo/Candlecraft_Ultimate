package hc.candlecraft.candlecraftultimate.feature_recipe.data.repository

import hc.candlecraft.candlecraftultimate.feature_recipe.data.data_source.IngredientDao
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl(
    private val dao: IngredientDao
): IngredientRepository {
    override fun getIngredients(): Flow<List<Ingredient>> {
        return dao.getAllIngredients()
    }

    override fun getAllIngredientsByRecipeId(recipeId: Int): Flow<List<Ingredient>> {
        return dao.getAllIngredientsByRecipeId(recipeId)
    }

    override suspend fun getIngredientByIdAndRecipeId(id: Int, recipeId: Int): Ingredient? {
        return dao.getIngredientByIdAndRecipeId(id, recipeId)
    }

    override suspend fun insertIngredient(ingredient: Ingredient) {
        dao.insertIngredient(ingredient)
    }

    override suspend fun deleteIngredient(ingredient: Ingredient) {
        dao.deleteIngredient(ingredient)
    }

    override suspend fun deleteAllIngredientsByRecipeId(recipeId: Int) {
        dao.deleteAllIngredientsByRecipeId(recipeId)
    }
}