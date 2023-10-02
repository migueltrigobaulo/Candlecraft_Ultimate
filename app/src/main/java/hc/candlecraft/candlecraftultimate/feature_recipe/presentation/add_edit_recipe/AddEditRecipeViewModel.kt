package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.common.MeasuringUnit
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case.IngredientUseCases
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.use_case.RecipeUseCases
import hc.candlecraft.candlecraftultimate.ui.theme.cardColorSchemeThemed
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases,
    private val ingredientUseCases: IngredientUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(IngredientsState())
    val state: State<IngredientsState> = _state

    private var currentRecipeId: Int? = null

    var isNew: Boolean = false

    private val _recipeName = MutableStateFlow("")
    val recipeName = _recipeName.asStateFlow()

    private val _fragrancePercentage = MutableStateFlow(0)
    val fragrancePercentage = _fragrancePercentage.asStateFlow()

    private val _recipeWaxType = MutableStateFlow("")
    val recipeWaxType = _recipeWaxType.asStateFlow()

    private val _selectedMeasuringUnit = MutableStateFlow<MeasuringUnit>(MeasuringUnit.Grams)
    val selectedMeasuringUnit: MutableStateFlow<MeasuringUnit> = _selectedMeasuringUnit

    private var recentlyDeletedRecipe: Recipe? = null

    private var getIngredientsJob: Job? = null

    init {
        savedStateHandle.get<Int>("recipeId")?.let { recipeId ->
            if (recipeId != -1) {
                viewModelScope.launch {
                    recipeUseCases.getRecipe(recipeId)?.also { recipe ->
                        currentRecipeId = recipe.id
                        _recipeName.value = recipe.name
                    }
                    getIngredients(recipeId)
                }
            }
        }
        savedStateHandle.get<Boolean>("isNew")?.let { isNew -> this.isNew = isNew}
    }

    fun setName(recipeName: String) {
        _recipeName.value = recipeName
    }
    fun setRecipeWaxType(recipeWaxType: String) {
        _recipeWaxType.value = recipeWaxType
    }
    fun setFragrancePercentage(fragrancePercentage: Int) {
        _fragrancePercentage.value = fragrancePercentage
    }

    private fun getIngredients(recipeId: Int) {
        getIngredientsJob?.cancel()
        getIngredientsJob = ingredientUseCases.getIngredients(recipeId).onEach { ingredients ->
            _state.value = _state.value.copy(ingredients = ingredients)
        }.launchIn(viewModelScope)
    }

    fun onSelectedMeasuringUnitsChanged(measuringUnit: MeasuringUnit) {
        _selectedMeasuringUnit.value = measuringUnit
    }

    fun addIngredient() {
        //TODO: REMOVE THIS
        val namesArray = arrayOf(
            "Parafina", "Perfume", "Colorante"
        )
        viewModelScope.launch {
            ingredientUseCases.insertIngredient(
                Ingredient(
                    recipeId = currentRecipeId,
                    name = namesArray.random(),
                    amount = 5F,
                    unitId = MeasuringUnit.Grams.id
                )
            )
        }
    }

    fun addRecipe() {
        //TODO: REMOVE THIS
        val namesArray = arrayOf(
            "Mango Tango", "First Date"
        )
        viewModelScope.launch {
            recipeUseCases.insertRecipe(
                Recipe(
                    name = namesArray[Random.nextInt().mod(namesArray.size)],
                    description = "Descripción",
                    lastEditTimestamp = System.currentTimeMillis(),
                    icon = arrayOf(
                        R.drawable.image_candle_1,
                        R.drawable.image_candle_2,
                        R.drawable.image_candle_3,
                        R.drawable.image_candle_4,
                        R.drawable.image_candle_5,
                        R.drawable.image_candle_6
                    ).random(), colorId = arrayOf(
                        cardColorSchemeThemed.red.id,
                        cardColorSchemeThemed.green.id,
                        cardColorSchemeThemed.pink.id,
                        cardColorSchemeThemed.blue.id
                    ).random()
                )
            )
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientUseCases.deleteIngredient(
                ingredient
            )
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeUseCases.deleteRecipe(
                recipe
            )
        }
    }
}