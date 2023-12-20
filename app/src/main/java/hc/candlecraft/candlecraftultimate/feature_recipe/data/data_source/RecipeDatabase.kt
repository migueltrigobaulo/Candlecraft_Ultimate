package hc.candlecraft.candlecraftultimate.feature_recipe.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hc.candlecraft.candlecraftultimate.common.Converters
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe

@Database(
    entities = [Recipe::class, Ingredient::class],
    version = 8, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val ingredientDao: IngredientDao

    companion object {
        const val DATABASE_NAME = "recipes_db"
    }
}