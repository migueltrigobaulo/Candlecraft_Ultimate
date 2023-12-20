package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe

import android.Manifest.permission.CAMERA
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import hc.candlecraft.candlecraftultimate.BuildConfig
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.common.AppSliderWithLabel
import hc.candlecraft.candlecraftultimate.common.AppTextField
import hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components.ImageContainer
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AddEditRecipeScreen(
    navController: NavController, viewModel: AddEditRecipeViewModel = hiltViewModel()
) {

    //ViewModel variables
    val recipeName = viewModel.recipeName.collectAsState()
    val recipePicture = viewModel.recipePicture.collectAsState()
    val recipeNotes = viewModel.recipeNotes.collectAsState()
    val fragranceName = viewModel.fragranceName.collectAsState()
    val recipeWaxType = viewModel.recipeWaxType.collectAsState()
    val fragrancePercentage = viewModel.fragrancePercentage.collectAsState()

    //ViewConstants
    val fragranceMaxPercentage = 24f

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val onEditModeState: MutableState<Boolean> = remember {
        mutableStateOf(viewModel.isNew)
    }

    val selectedMeasuringUnit = viewModel.selectedMeasuringUnit.collectAsState().value

    rememberCoroutineScope()

    MaterialTheme {
        Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            TopAppBar(scrollBehavior = scrollBehavior, title = {
                Text(
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
            })
        }, floatingActionButton = {
            FloatingActionButton(shape = RoundedCornerShape(15.dp), onClick = {
                viewModel.addRecipe()
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(id = R.string.add_ingredient)
                )
            }
        }) { paddingValues ->

            val context = LocalContext.current
            val file = context.createImageFile()
            val uri = FileProvider.getUriForFile(
                Objects.requireNonNull(context), BuildConfig.APPLICATION_ID + ".provider", file
            )

            val cameraLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
                    viewModel.setRecipePicture(uri)
                }

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
                cameraLauncher.launch(uri)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(paddingValues)
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top
            ) {


                item {
                    if (recipePicture.value?.path?.isNotEmpty() == true) {
                        ImageContainer(modifier = Modifier.height(250.dp), onClickAction = {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(context, CAMERA)
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                // Request a permission
                                permissionLauncher.launch(CAMERA)
                            }
                        }) {
                            Image(
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                painter = rememberAsyncImagePainter(recipePicture.value),
                                contentDescription = null
                            )
                        }
                    } else {
                        ImageContainer(onClickAction = {
                            val permissionCheckResult =
                                ContextCompat.checkSelfPermission(context, CAMERA)
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                cameraLauncher.launch(uri)
                            } else {
                                // Request a permission
                                permissionLauncher.launch(CAMERA)
                            }
                        }) {
                            Icon(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .size(45.dp),
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = stringResource(id = R.string.add_ingredient)
                            )
                        }

                    }


                    Spacer(modifier = Modifier.height(15.dp))

                    AppTextField(fieldName = stringResource(id = R.string.recipe_name),
                        value = recipeName,
                        onValueChange = { newValue: String -> viewModel.setName(newValue) })

                    Spacer(modifier = Modifier.height(15.dp))

                    AppTextField(
                        fieldName = stringResource(id = R.string.recipe_notes),
                        value = recipeNotes,
                        onValueChange = { newValue: String -> viewModel.setNotes(newValue) },
                        maxLines = 5
                    )

                }

                items(viewModel.ingredients.value.ingredients.size) {
                    Spacer(modifier = Modifier.height(15.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AppTextField(fieldName = stringResource(id = R.string.fragrance_name),
                        value = fragranceName,
                        onValueChange = { newValue: String ->
                            viewModel.setFragranceName(
                                newValue
                            )
                        })


                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = stringResource(R.string.fragrance_amount))

                    AppSliderWithLabel(
                        fragrancePercentage.value,
                        onValueChange = { newValue -> viewModel.setFragrancePercentage(newValue) },
                        valueRange = 0f..fragranceMaxPercentage
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(15.dp))

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            viewModel.addIngredient()
                        }) {
                        Text(text = stringResource(id = R.string.add_ingredient))
                    }
                }
            }
        }
    }
}




