package hc.candlecraft.candlecraftultimate.common

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import hc.candlecraft.candlecraftultimate.R

@Composable
fun AppAutocompleteDropdown(
    fieldName: String,
    dropdownCurrentValue: State<String>?,
    dropdownSuggestions: ArrayList<String>,
    onValueChange: (String) -> Unit
) {

    var rowSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
        rowSize = layoutCoordinates.size.toSize()
    }) {
        var expanded by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = dropdownCurrentValue?.value ?: "",
            shape = RoundedCornerShape(15.dp),
            onValueChange = { newValue: String ->
                onValueChange(newValue)
            },
            label = { Text(text = fieldName) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    expanded = focusState.isFocused && dropdownSuggestions.size > 0
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { },
            modifier = Modifier
                .width(with(LocalDensity.current) { rowSize.width.toDp() })

                .padding(horizontal = 15.dp),
            properties = PopupProperties(focusable = false)
        ) {
            dropdownSuggestions.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        onValueChange(label)
                        focusManager.clearFocus()
                    }, trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.add_ingredient),
                            modifier = Modifier.clickable {
                                dropdownSuggestions.remove(label)
                                expanded = false
                                expanded = dropdownSuggestions.size > 0
                            }
                        )
                    })
            }
        }
    }
}