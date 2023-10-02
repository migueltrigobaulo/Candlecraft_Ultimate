package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.feature_login.presentation.login.EmailField
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components.IngredientItem


@Preview
@Composable
private fun AddEditRecipeScreenPreview(){
    AddEditRecipeScreen(rememberNavController())
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class
)
@Composable
fun AddEditRecipeScreen(
    navController: NavController, viewModel: AddEditRecipeViewModel = hiltViewModel()
) {


    //ViewModel variables
    val recipeName = viewModel.recipeName.collectAsState()
    val recipeWaxType = viewModel.recipeWaxType.collectAsState()
    val fragrancePercentage = viewModel.fragrancePercentage.collectAsState()

    //ViewConstants
    val FRAGRANCE_MAX_PERCENTAGE = 24f

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val newIngredientDialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val onEditModeState: MutableState<Boolean> = remember {
        mutableStateOf(viewModel.isNew)
    }

    val expandedFabState = remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction < .5F
        }
    }


    var imageUri by remember { mutableStateOf<Uri?>(null) }

    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val selectedMeasuringUnit = viewModel.selectedMeasuringUnit.collectAsState().value

    rememberCoroutineScope()

    MaterialTheme {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            TopAppBar(scrollBehavior = scrollBehavior, title = {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    text = if (viewModel.isNew) stringResource(id = R.string.new_recipe) else recipeName.value
                )
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }, actions = {
                IconButton(onClick = { onEditModeState.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            })
        }, floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = expandedFabState.value,
                shape = RoundedCornerShape(15.dp),
                icon = {
                    AnimatedContent(
                        targetState = onEditModeState.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(150)) togetherWith
                                    fadeOut(animationSpec = tween(150))
                        }, label = "top right action icon animation"
                    ) { onEditMode ->
                        when (onEditMode) {
                            true -> {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(id = R.string.add_ingredient)
                                )
                            }

                            false -> {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = stringResource(id = R.string.start_batch)
                                )
                            }
                        }
                    }
                },
                text = {
                    AnimatedContent(
                        targetState = onEditModeState.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(150)) with
                                    fadeOut(animationSpec = tween(150))
                        }, label = "fab content change animation"
                    ) { onEditMode ->
                        when (onEditMode) {
                            true -> {
                                Text(
                                    modifier = Modifier.padding(bottom = 5.dp),
                                    text = stringResource(id = R.string.add_ingredient)
                                )
                            }

                            false -> {
                                Text(
                                    modifier = Modifier.padding(bottom = 5.dp),
                                    text = stringResource(id = R.string.start_batch)
                                )
                            }
                        }
                    }
                },
                onClick = {
                    viewModel.addIngredient()
                })

        }) { paddingValues ->
            if (newIngredientDialogState.value) {
                Dialog(
                    onDismissRequest = { newIngredientDialogState.value = false },
                    content = {
                        ElevatedCard(Modifier.fillMaxWidth()) {
                            Column(
                                Modifier.padding(
                                    vertical = 10.dp, horizontal = 10.dp
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier.weight(1F),
                                        text = stringResource(id = R.string.add_ingredient)
                                    )
                                    IconButton(modifier = Modifier.align(Alignment.CenterVertically),
                                        onClick = { newIngredientDialogState.value = false }) {
                                        Icon(
                                            imageVector = Icons.Default.Cancel,
                                            contentDescription = "Go back",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                                EmailField(email = "", onTextFieldChanged = {})
                                Spacer(modifier = Modifier.height(5.dp))
                                EmailField(email = "", onTextFieldChanged = {})
                            }
                        }
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true, dismissOnClickOutside = true
                    )
                )
            }
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {

                    var rowSize by remember { mutableStateOf(Size.Zero) }

                    OutlinedTextField(
                        value = recipeName.value,
                        shape = RoundedCornerShape(15.dp),
                        onValueChange = { newValue -> viewModel.setName(newValue) },
                        singleLine = true,
                        maxLines = 1,
                        label = { Text(text = stringResource(id = R.string.recipe_name)) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        rowSize = layoutCoordinates.size.toSize()
                    }) {
                        var expanded by remember { mutableStateOf(false) }
                        val focusManager = LocalFocusManager.current

                        OutlinedTextField(
                            value = recipeWaxType.value,
                            shape = RoundedCornerShape(15.dp),
                            onValueChange = { newValue: String ->
                                viewModel.setRecipeWaxType(
                                    newValue
                                )
                            },
                            label = { Text(text = stringResource(id = R.string.wax_type)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { focusState ->
                                    expanded = focusState.isFocused
                                }
                        )

                        val suggestions = arrayListOf("Parafina", "Cera de abeja")

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { },
                            modifier = Modifier
                                .width(with(LocalDensity.current) { rowSize.width.toDp() })

                                .padding(horizontal = 15.dp),
                            properties = PopupProperties(focusable = false)
                        ) {
                            suggestions.forEach { label ->
                                DropdownMenuItem(
                                    text = { Text(text = label) },
                                    onClick = {
                                        viewModel.setRecipeWaxType(label)
                                        focusManager.clearFocus()
                                    })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(text = stringResource(R.string.fragance_amount))
                        SliderWithLabel(
                            fragrancePercentage.value.toFloat(),
                            onValueChange = { newValue -> viewModel.setFragrancePercentage(newValue) },
                            valueRange = 0f..FRAGRANCE_MAX_PERCENTAGE
                        )
                    }


                    var selectedType by remember { mutableIntStateOf(0) }

                    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp), content = {
                        items(viewModel.state.value.ingredients.size) { index ->
                            val ingredient = viewModel.state.value.ingredients[index]
                            IngredientItem(
                                ingredient = ingredient,
                                selectedMeasuringUnit = selectedMeasuringUnit
                            ) { viewModel.deleteIngredient(ingredient) }
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun SliderWithLabel(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    labelMinWidth: Dp = 30.dp,
    onValueChange: (Int) -> Unit
) {

    val primary = MaterialTheme.colorScheme.primary.toArgb()
    val onPrimary = MaterialTheme.colorScheme.onPrimary.toArgb()

    Column {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            val offset = getSliderOffset(
                value = value,
                valueRange = valueRange,
                boxWidth = maxWidth,
                labelWidth = labelMinWidth
            )

            val endValueText = value.toInt().toString()


            SliderLabel(
                color = primary, fontColor = onPrimary,
                label = endValueText, minWidth = labelMinWidth, modifier = Modifier
                    .padding(horizontal = offset)
            )

        }

        Slider(
            colors = SliderDefaults.colors(
                thumbColor = Color(primary),
                activeTrackColor = Color(primary)
            ),
            value = value, onValueChange = {
                onValueChange(it.toInt())
            },
            valueRange = valueRange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

    }
}


@Composable
fun SliderLabel(
    label: String,
    minWidth: Dp,
    modifier: Modifier = Modifier,
    color: Int,
    fontColor: Int
) {
    Text(
        label,
        textAlign = TextAlign.Center,
        color = Color(fontColor),
        modifier = modifier
            .background(
                color = Color(color),
                shape = CircleShape
            )
            .padding(horizontal = 4.dp)
            .padding(bottom = 5.dp)
            .defaultMinSize(minWidth = minWidth)
    )
}


private fun getSliderOffset(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    boxWidth: Dp,
    labelWidth: Dp
): Dp {

    val coerced = value.coerceIn(valueRange.start, valueRange.endInclusive)
    val positionFraction = calcFraction(valueRange.start, valueRange.endInclusive, coerced)

    return (boxWidth - labelWidth) * positionFraction
}


// Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

private fun calcFractionExp(a: Float, b: Float, pos: Float): Float {
    val exponentialFactor = 3.5f // Exponential factor to control the rate of change

    val normalizedPos = (pos - a) / (b - a) // Normalize the position between a and b

    val fraction = Math.pow(normalizedPos.toDouble(), exponentialFactor.toDouble()).toFloat()
    return fraction.coerceIn(0f, 1f) // Clamp the fraction between 0 and 1
}

