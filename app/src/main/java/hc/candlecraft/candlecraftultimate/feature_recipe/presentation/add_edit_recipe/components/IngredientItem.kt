package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hc.candlecraft.candlecraftultimate.common.MeasuringUnit
import hc.candlecraft.candlecraftultimate.common.ThemeColorUtils
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Ingredient
import hc.candlecraft.candlecraftultimate.ui.theme.cardColorSchemeThemed

@Preview
@Composable
fun IngredientItem(
    ingredient: Ingredient = Ingredient(1, 1, "Parafina", 5F, MeasuringUnit.Grams.id),
    color: Color = ThemeColorUtils.getColorById(cardColorSchemeThemed.blue.id),
    selectedMeasuringUnit: MeasuringUnit = MeasuringUnit.Grams,
    onDelete: () -> Unit = {}
) {

    var text by remember { mutableStateOf("") }
    var isVisible by remember {
        mutableStateOf(true)
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(.6F),
            label = { Text(text = "") },
            value = ingredient.name,
            onValueChange = {},
            shape = RoundedCornerShape(15.dp), readOnly = true
        )
        
        Spacer(modifier = Modifier.width(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .weight(.3F)
                .onFocusChanged { isVisible = it.isFocused || text.isNotBlank() },
            value = text,
            label = { Text(text = "Cantidad") },
            textStyle = MaterialTheme.typography.bodyLarge,
            visualTransformation = if (isVisible &&
                (ingredient.unitId == MeasuringUnit.Grams.id
                        || ingredient.unitId == MeasuringUnit.Ounces.id
                        || ingredient.unitId == MeasuringUnit.Percentage.id)
            ) {
                SuffixVisualTransformation(
                    "   " + stringResource(
                        id = when (ingredient.unitId) {
                            MeasuringUnit.Grams.id -> MeasuringUnit.Grams.abbreviatedName
                            MeasuringUnit.Ounces.id -> MeasuringUnit.Ounces.abbreviatedName
                            MeasuringUnit.Percentage.id -> MeasuringUnit.Percentage.abbreviatedName
                            else -> {
                                MeasuringUnit.Percentage.abbreviatedName
                            }
                        }
                    )
                )
            } else SuffixVisualTransformation(""),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { text = it },
            shape = RoundedCornerShape(15.dp)
        )
    }
}

class SuffixVisualTransformation(private val suffix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = text + AnnotatedString(suffix, SpanStyle(Color.Gray))

        return TransformedText(transformedText, SuffixOffsetMapping(text.text))
    }
}

class SuffixOffsetMapping(private val originalText: String) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int = offset

    override fun transformedToOriginal(offset: Int): Int {
        return if (offset > originalText.length) originalText.length else offset
    }
}