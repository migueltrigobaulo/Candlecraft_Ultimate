package hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case

data class IngredientUseCases(
    val getIngredients: GetIngredients,
    val insertIngredient: InsertIngredient,
    val deleteIngredient: DeleteIngredient
)
