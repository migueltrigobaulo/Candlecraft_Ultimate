package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecipeTop(modifier: Modifier, recipeName: State<String>) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)

            .padding(top = 40.dp, start = 15.dp, end = 15.dp, bottom = 15.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.weight(1F),
                placeholder = {
                    Text(text = recipeName.value, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineMedium)
                },
                textStyle = MaterialTheme.typography.headlineMedium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                ),
                value = "",
                onValueChange = {})
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    tint = Color.White
                )
            }
        }
    }

}