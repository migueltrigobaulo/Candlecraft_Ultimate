package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.ui.theme.*
import java.sql.Date
import java.sql.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RecipeItem(
    modifier: Modifier = Modifier,
    recipe: Recipe = Recipe(
        name = "Mango Tango jhjhhhj",
        description = "Descripción",
        lastEditTimestamp = System.currentTimeMillis(),
        icon = arrayOf(
            R.drawable.image_candle_1,
            R.drawable.image_candle_2,
            R.drawable.image_candle_3,
            R.drawable.image_candle_4,
            R.drawable.image_candle_5,
            R.drawable.image_candle_6
        ).random(), colorId = arrayOf(
            BackgroundColorDarkRed,
            BackgroundColorDarkBlue,
            BackgroundColorLightGreen,
            BackgroundColorLightPink
        ).random().toArgb()
    ),
    onClick: () -> Unit = { }
) {

    ElevatedCard(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth(),
        onClick = { onClick() },
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(
            modifier
                .padding(vertical = 10.dp)
                .padding(bottom = 5.dp)
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

                Icon(
                    painter = painterResource(id = recipe.icon),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(size = 70.dp).padding(end = 15.dp)
                )
                Column(
                modifier = Modifier
                    .fillMaxWidth()
                ) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Start, softWrap = true,
                    text = recipe.name,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                val stamp = Timestamp(recipe.lastEditTimestamp)
                val date = Date(stamp.time)

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center, softWrap = true,
                    text = recipe.description,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


        }
    }
}