package hc.candlecraft.candlecraftultimate.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import hc.candlecraft.candlecraftultimate.feature_login.presentation.login.LoginScreen
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.AddEditRecipeScreen
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes.RecipesScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route,
        enterTransition = {
                 fadeIn(animationSpec = tween(500))
        },
        exitTransition = { fadeOut(animationSpec = tween(500)) }
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
                }
            )
        ) {
            AddEditRecipeScreen(
                navController = navController
            )
        }
    }
}