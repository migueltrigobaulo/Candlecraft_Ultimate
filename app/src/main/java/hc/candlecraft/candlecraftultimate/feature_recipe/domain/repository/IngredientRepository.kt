package hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository

import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getIngredients(): Flow<List<Ingredient>>

    fun getAllIngredientsByRecipeId(recipeId: Int): Flow<List<Ingredient>>

    suspend fun getIngredientByIdAndRecipeId(id: Int, recipeId: Int): Ingredient?

    suspend fun insertIngredient(ingredient: Ingredient)

    suspend fun deleteIngredient(ingredient: Ingredient)

    suspend fun deleteAllIngredientsByRecipeId(recipeId: Int)
}