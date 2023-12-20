package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case.RecipeUseCases
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.OrderType
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.util.RecipeOrder
import hc.candlecraft.candlecraftultimate.ui.theme.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
) : ViewModel() {


    val iconIDs = arrayOf(
        R.drawable.image_candle_1,
        R.drawable.image_candle_2,
        R.drawable.image_candle_3,
        R.drawable.image_candle_4,
        R.drawable.image_candle_5,
        R.drawable.image_candle_6
    )
    val colorIds =
        arrayOf(
            BackgroundColorLightRed,
            BackgroundColorLightBlue,
            BackgroundColorLightGreen,
            BackgroundColorLightPink
        )


    private val _state = mutableStateOf(RecipesState())
    val state: State<RecipesState> = _state

    private val _searchString = MutableStateFlow("")
    val searchString = _searchString.asStateFlow()

    private var recentlyDeletedRecipe: Recipe? = null

    private var getRecipesJob: Job? = null

    init {
        getRecipes(RecipeOrder.Date(OrderType.Ascending))
    }

    fun onSearchStringChanged(searchString: String) {
        _searchString.value = searchString
    }

    fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.Order -> {
                if (event.recipeOrder::class == state.value.recipeOrder::class &&
                    event.recipeOrder.orderType == state.value.recipeOrder.orderType
                ) {
                    return
                }

            }
            is RecipesEvent.SearchByName -> {
                getRecipes(_state.value.recipeOrder, event.recipeName)
            }
            is RecipesEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    recipeUseCases.deleteRecipe(event.recipe)
                    recentlyDeletedRecipe = event.recipe
                }
            }
            is RecipesEvent.RestoreRecipe -> {
                viewModelScope.launch {
                    recentlyDeletedRecipe?.let { recipeUseCases.insertRecipe(it) }
                    recentlyDeletedRecipe = null
                }
            }
            RecipesEvent.TogleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeUseCases.deleteRecipe(
                recipe
            )
        }
    }

    private fun getRecipes(recipeOrder: RecipeOrder, searchString: String? = null) {
        getRecipesJob?.cancel()
        getRecipesJob = recipeUseCases.getRecipes(recipeOrder).onEach { recipes ->
            _state.value = state.value.copy(
                recipes = recipes.filter { recipe -> searchString==null || recipe.name.contains(searchString) },
                noRecipesInDB = recipes.isEmpty(),
                recipeOrder = recipeOrder
            )
        }.launchIn(viewModelScope)
    }
}