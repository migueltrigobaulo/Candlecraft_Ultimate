package hc.candlecraft.candlecraftultimate.feature_recipe.domain.util

sealed class RecipeOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): RecipeOrder(orderType)
    class Date(orderType: OrderType): RecipeOrder(orderType)
}
