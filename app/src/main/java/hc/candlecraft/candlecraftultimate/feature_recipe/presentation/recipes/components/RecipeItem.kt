package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.recipes.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import hc.candlecraft.candlecraftultimate.R
import hc.candlecraft.candlecraftultimate.feature_recipe.domain.model.Recipe
import hc.candlecraft.candlecraftultimate.ui.theme.*
import java.sql.Date
import java.sql.Timestamp
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview()
@Composable
fun RecipeItem(
    modifier: Modifier = Modifier, recipe: Recipe = Recipe(
        name = "Mango Tango",
        notes = "Descripción \n asd",
        lastEditTimestamp = System.currentTimeMillis(),
        icon = arrayOf(
            R.drawable.image_candle_1,
            R.drawable.image_candle_2,
            R.drawable.image_candle_3,
            R.drawable.image_candle_4,
            R.drawable.image_candle_5,
            R.drawable.image_candle_6
        ).random(),
        waxType = "Parafina",
        fragranceName = "Frambuesa",
        fragrancePercentage = 12,
        recipePicture = null
    ), onClick: () -> Unit = { }
) {

    ElevatedCard(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth(),
        onClick = { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(25.dp)
    ) {
        Row(
            modifier
                .padding(vertical = 10.dp)
                .padding(start = 20.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    val stamp = Timestamp(recipe.lastEditTimestamp)
                    val date = Date(stamp.time)

                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Start,
                        softWrap = true,
                        text = recipe.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        },
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (!recipe.notes.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            softWrap = true,
                            text = recipe.notes,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        softWrap = true,
                        text = date.toString(),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                }

                if (recipe.recipePicture!=null) {
                    Image(modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .border(2.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f), RoundedCornerShape(25.dp)),
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(recipe.recipePicture),
                        contentDescription = null
                    )
                }
            }

        }
    }
}