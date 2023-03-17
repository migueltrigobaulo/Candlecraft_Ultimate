package hc.candlecraft.candlecraftultimate.feature_recipe.data.data_source

import androidx.room.*
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Query("SELECT * FROM Ingredient")
    fun getAllIngredients(): Flow<List<Ingredient>>

    @Query("SELECT * FROM Ingredient WHERE recipeId = :recipeId")
    fun getAllIngredientsByRecipeId(recipeId: Int): Flow<List<Ingredient>>

    @Query("DELETE FROM Ingredient WHERE recipeId = :recipeId")
    fun deleteAllIngredientsByRecipeId(recipeId: Int)

    @Query("SELECT * FROM Ingredient WHERE id = :id and recipeId = :recipeId")
    suspend fun getIngredientByIdAndRecipeId(id: Int, recipeId: Int): Ingredient?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)
}