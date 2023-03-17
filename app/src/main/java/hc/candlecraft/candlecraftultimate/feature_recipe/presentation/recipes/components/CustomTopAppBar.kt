package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes.components

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hc.candlecraft.candlecraftultimate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    searchString: String? = null,
    onValueChange: (searchString: String) -> Unit = {}
) {

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(true) }

    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(top = 40.dp, start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .focusable(isFocused, interactionSource).background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        readOnly = !isFocused,
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = {
                focusRequester.requestFocus()
                isFocused = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        value = searchString ?: "",
        placeholder = {
            Image(
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.candlecraft_logo_name),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Login Logo"
            )
        },
        onValueChange = {
            onValueChange(it)
        })
}