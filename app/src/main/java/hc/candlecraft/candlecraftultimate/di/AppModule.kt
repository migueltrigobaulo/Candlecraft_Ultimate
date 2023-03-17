package hc.candlecraft.candlecraftultimate.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hc.candlecraft.candlecraftultimate.feature_recipe.data.data_source.RecipeDatabase
import hc.candlecraft.candlecraftultimate.feature_recipe.data.repository.IngredientRepositoryImpl
import hc.candlecraft.candlecraftultimate.feature_recipe.data.repository.RecipeRepositoryImpl
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.IngredientRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.repository.RecipeRepository
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            RecipeDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(db: RecipeDatabase): RecipeRepository {
        return RecipeRepositoryImpl(db.recipeDao)
    }

    @Provides
    @Singleton
    fun provideIngredientRepository(db: RecipeDatabase): IngredientRepository {
        return IngredientRepositoryImpl(db.ingredientDao)
    }

    @Provides
    @Singleton
    fun provideRecipeUseCases(recipeRepository: RecipeRepository): RecipeUseCases {
        return RecipeUseCases(
            getRecipes = GetRecipes(recipeRepository),
            getRecipe = GetRecipe(recipeRepository),
            deleteRecipe = DeleteRecipe(recipeRepository),
            insertRecipe = InsertRecipe(recipeRepository)
        )
    }

    @Provides
    @Singleton
    fun provideIngredientUseCases(ingredientRepository: IngredientRepository): IngredientUseCases {
        return IngredientUseCases(
            getIngredients = GetIngredients(ingredientRepository),
            insertIngredient = InsertIngredient(ingredientRepository),
            deleteIngredient = DeleteIngredient(ingredientRepository)
        )
    }
}