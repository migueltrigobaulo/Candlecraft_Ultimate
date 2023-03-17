package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.common.MeasuringUnit
import hc.candlecraft.candlecraftultimate.feature_login.presentation.login.EmailField
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components.IngredientItem
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components.SegmentedControl
import hc.candlecraft.candlecraftultimate.navigation.Screen

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class
)
@Composable
fun AddEditRecipeScreen(
    navController: NavController, viewModel: AddEditRecipeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val newIngredientDialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val onEditModeState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val expandedFabState = remember {
        derivedStateOf {
            scrollBehavior.state.collapsedFraction < .5F
        }
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    val selectedMeasuringUnit = viewModel.selectedMeasuringUnit.collectAsState().value

    val scope = rememberCoroutineScope()

    MaterialTheme {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            TopAppBar(scrollBehavior = scrollBehavior, title = {
                Text(
                    text = viewModel.recipeName.value
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
                AnimatedContent(targetState = onEditModeState.value) { onEditMode ->
                    when (onEditMode) {
                        false -> {
                            IconButton(onClick = { onEditModeState.value = true }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        true -> {
                            IconButton(onClick = { onEditModeState.value = false }) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = "Go back",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            })
        }, floatingActionButton = {
            AnimatedContent(targetState = onEditModeState.value) { onEditMode ->
                when (onEditMode) {
                    true -> {
                        ExtendedFloatingActionButton(expanded = expandedFabState.value,
                            shape = RoundedCornerShape(15.dp),
                            icon = {

                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(id = R.string.add_ingredient)
                                )
                            },
                            text = {
                                Text(
                                    modifier = Modifier.padding(bottom = 5.dp),
                                    text = stringResource(id = R.string.add_ingredient)
                                )
                            },
                            onClick = {
                                viewModel.addRecipe()
                                navController.navigate(Screen.RecipesScreen.route)
                            })
                    }
                    false -> {
                        ExtendedFloatingActionButton(expanded = expandedFabState.value,
                            shape = RoundedCornerShape(15.dp),
                            icon = {

                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = stringResource(id = R.string.start_batch)
                                )
                            },
                            text = {
                                Text(
                                    modifier = Modifier.padding(bottom = 5.dp),
                                    text = stringResource(id = R.string.start_batch)
                                )
                            },
                            onClick = {
                                viewModel.addRecipe()
                                navController.navigate(Screen.RecipesScreen.route)
                            })
                    }
                }
            }
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
                                EmailField(email = "", onTextFieldChanged = {})
                                Spacer(modifier = Modifier.height(5.dp))
                                EmailField(email = "", onTextFieldChanged = {})
                                Spacer(modifier = Modifier.height(5.dp))
                                EmailField(email = "", onTextFieldChanged = {})
                                Spacer(modifier = Modifier.height(5.dp))
                                Button(modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 50.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    onClick = { }) {
                                    Text(text = "Añadir Ingrediente")
                                }
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
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SegmentedControl(Modifier.padding(horizontal = 10.dp),
                    useFixedWidth = true,
                    items = listOf(
                        MeasuringUnit.Grams, MeasuringUnit.Ounces, MeasuringUnit.Percentage
                    ),
                    onItemSelection = { selectedItemType ->
                        viewModel.onSelectedMeasuringUnitsChanged(
                            selectedItemType
                        )
                    })
                Spacer(modifier = Modifier.padding(5.dp))
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {

                    var selectedType by remember { mutableStateOf(0) }

                    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp), content = {
                        items(viewModel.state.value.ingredients.size) { index ->
                            val ingredient = viewModel.state.value.ingredients[index]
                            IngredientItem(
                                ingredient = ingredient,
                                selectedMeasuringUnit = selectedMeasuringUnit
                            ) { viewModel.deleteIngredient(ingredient) }
                        }

                        item {
                            AnimatedVisibility(visible = onEditModeState.value) {
                                Column {
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Button(modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp), onClick = { viewModel.addIngredient() }, shape = RoundedCornerShape(15.dp)) {
                                        Text(text = stringResource(id = R.string.add_ingredient))
                                    }
                                }

                            }
                        }
                    })
                }
            }
        }
    }
}

