package hc.candlecraft.candlecraftultimate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hc.candlecraft.candlecraftultimate.feature_login.presentation.login.LoginScreen
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.AddEditRecipeScreen
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes.RecipesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RecipesScreen.route) {
            RecipesScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditRecipeScreen.route +
                    "?recipeId={recipeId}",
            arguments = listOf(
                navArgument(
                    name = "recipeId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "isNew"
                ) {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) {
            AddEditRecipeScreen(
                navController = navController
            )
        }
    }
}
