package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import hc.candlecraft.candlecraftultimate.MainActivity
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes.components.RecipeItem
import hc.candlecraft.candlecraftultimate.navigation.Screen
import hc.candlecraft.candlecraftultimate.ui.theme.*


@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun RecipesScreen(
    navController: NavController, viewModel: RecipesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val scroll: ScrollState = rememberScrollState(0)

    val iconIDs = arrayOf(
        R.drawable.image_candle_1,
        R.drawable.image_candle_2,
        R.drawable.image_candle_3,
        R.drawable.image_candle_4,
        R.drawable.image_candle_5,
        R.drawable.image_candle_6
    )
    val colorIds = if (isSystemInDarkTheme()) {
        arrayOf(
            BackgroundColorDarkRed,
            BackgroundColorDarkBlue,
            BackgroundColorDarkGreen,
            BackgroundColorDarkPink
        )
    } else {
        arrayOf(
            BackgroundColorLightRed,
            BackgroundColorLightBlue,
            BackgroundColorLightGreen,
            BackgroundColorLightPink
        )
    }

    val searchString: String by viewModel.searchString.collectAsState(initial = "")

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val expandedFabState = remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction < .5F
        }
    }

    val activity = LocalContext.current as MainActivity


    MaterialTheme {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    expanded = expandedFabState.value,
                    shape = RoundedCornerShape(15.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.new_recipe)
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(id = R.string.new_recipe)
                        )
                    },
                    onClick = {
                        navController.navigate(
                            Screen.AddEditRecipeScreen.route
                        )
                    })

            },
            topBar = {
                TopAppBar(
                    title = {
                        Image(
                            modifier = Modifier
                                .height(35.dp)
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.candlecraft_logo_name),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                            contentDescription = "Candlecraft"
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { activity.changeTheme() }) {
                            Icon(
                                imageVector = Icons.Filled.LightMode,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )

            },
            content = { paddingValues ->
                LazyVerticalGrid(
                    modifier = Modifier.padding(paddingValues),
                    columns = GridCells.Fixed(1),
                    content = {
                        items(viewModel.state.value.recipes.size) { index ->
                            val recipe = viewModel.state.value.recipes[index]
                            RecipeItem(
                                modifier = Modifier,
                                recipe = recipe,
                                onClick = {
                                    navController.navigate(
                                        Screen.AddEditRecipeScreen.route +
                                                "?recipeId=${recipe.id}"
                                    )
                                }
                            )
                        }
                    })
            }
        )
    }
}

