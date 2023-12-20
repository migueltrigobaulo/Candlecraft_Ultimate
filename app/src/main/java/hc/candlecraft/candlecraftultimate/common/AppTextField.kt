package hc.candlecraft.candlecraftultimate.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hc.candlecraft.candlecraftultimate.R

@Composable
fun AppTextField(
    fieldName: String = "",
    value: State<String>,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value.value,
        shape = RoundedCornerShape(15.dp),
        onValueChange = { newValue -> onValueChange(newValue) },
        singleLine = maxLines == 1,
        maxLines = maxLines,
        label = { Text(text = fieldName) },
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AppNumericField(
    fieldName: String = "",
    value: State<Float>,
    onValueChange: (Float) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    OutlinedTextField(
        value = value.value.toString(),
        shape = RoundedCornerShape(15.dp),
        placeholder = { Text(text = "0.0") },
        onValueChange = { newValue -> onValueChange(newValue.toFloat()) },
        singleLine = true,
        maxLines = 1,
        label = { Text(text = fieldName) },
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun tftest() {
    val str by remember {
        mutableStateOf("a")
    }
    TextField(value = "", onValueChange = {}, placeholder = { Text("Enter Email") })
}