package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

data class RecipeUseCases(
    val getRecipes: GetRecipes,
    val getRecipe: GetRecipe,
    val deleteRecipe: DeleteRecipe,
    val insertRecipe: InsertRecipe
)
