package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe

import android.net.Uri
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases,
    private val ingredientUseCases: IngredientUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _ingredients = mutableStateOf(IngredientsState())
    val ingredients: State<IngredientsState> = _ingredients

    private var currentRecipeId: Int? = null

    var isNew: Boolean = false

    private val _recipeName = MutableStateFlow("")
    val recipeName = _recipeName.asStateFlow()

    private val _recipePicture = MutableStateFlow<Uri?>(null)
    val recipePicture = _recipePicture.asStateFlow()

    private val _recipeNotes = MutableStateFlow("")
    val recipeNotes = _recipeNotes.asStateFlow()

    private val _fragranceName = MutableStateFlow("")
    val fragranceName = _fragranceName.asStateFlow()

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
                        _recipeWaxType.value = recipe.waxType
                        _recipeNotes.value = recipe.notes
                        _fragranceName.value = recipe.fragranceName
                        _fragrancePercentage.value = recipe.fragrancePercentage
                        _recipePicture.value = recipe.recipePicture
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

    fun setNotes(recipeNotes: String) {
        _recipeNotes.value = recipeNotes
    }
    fun setFragranceName(fragranceName: String) {
        _fragranceName.value = fragranceName
    }
    fun setRecipeWaxType(recipeWaxType: String) {
        _recipeWaxType.value = recipeWaxType
    }
    fun setRecipePicture(recipePicture: Uri) {
        _recipePicture.value = recipePicture
    }
    fun setFragrancePercentage(fragrancePercentage: Int) {
        _fragrancePercentage.value = fragrancePercentage
    }

    private fun getIngredients(recipeId: Int) {
        getIngredientsJob?.cancel()
        getIngredientsJob = ingredientUseCases.getIngredients(recipeId).onEach { ingredients ->
            _ingredients.value = _ingredients.value.copy(ingredients = ArrayList(ingredients))
        }.launchIn(viewModelScope)
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
                    amount = 5F
                )
            )
        }
    }

    fun addRecipe() {
        viewModelScope.launch {
            recipeUseCases.insertRecipe(
                Recipe(
                    id = currentRecipeId,
                    name = recipeName.value,
                    notes = recipeNotes.value,
                    lastEditTimestamp = System.currentTimeMillis(),
                    icon = arrayOf(
                        R.drawable.image_candle_1,
                        R.drawable.image_candle_2,
                        R.drawable.image_candle_3,
                        R.drawable.image_candle_4,
                        R.drawable.image_candle_5,
                        R.drawable.image_candle_6
                    ).random(),
                    fragranceName = fragranceName.value,
                    waxType = recipeWaxType.value,
                    fragrancePercentage = _fragrancePercentage.value,
                    recipePicture = _recipePicture.value
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