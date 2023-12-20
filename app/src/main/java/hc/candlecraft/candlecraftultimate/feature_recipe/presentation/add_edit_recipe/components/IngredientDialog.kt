package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.common.AppNumericField
import hc.candlecraft.candlecraftultimate.common.AppTextField
import hc.candlecraft.candlecraftultimate.common.CustomButton
import hc.candlecraft.candlecraftultimate.common.MeasuringUnit

@Composable
fun IngredientDialog(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
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
                            onClick = { onDismissRequest() }) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Go back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    val ingredientName = remember {
                        mutableStateOf("")
                    }
                    val ingredientAmount = remember {
                        mutableFloatStateOf(0f)
                    }
                    val unitsName = stringResource(id = MeasuringUnit.Grams.name)
                    val ingredientUnits = remember {
                        mutableStateOf(unitsName)
                    }
                    val selectedUnitId = remember {
                        mutableIntStateOf(MeasuringUnit.Grams.id)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    AppTextField(fieldName = stringResource(id = R.string.name), value = ingredientName, onValueChange = { newValue ->
                        ingredientName.value = newValue
                    })
                    Spacer(modifier = Modifier.height(5.dp))
                    AppNumericField(fieldName = stringResource(id = R.string.amount), value = ingredientAmount, onValueChange = { newValue ->
                        ingredientAmount.value = newValue
                    },
                    keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CustomButton(text = stringResource(id = R.string.add_ingredient))
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    )
}