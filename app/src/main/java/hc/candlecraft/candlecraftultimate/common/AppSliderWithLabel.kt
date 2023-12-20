package hc.candlecraft.candlecraftultimate.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun SliderWithLabelPreview() {
    var sliderValue by remember {
        mutableIntStateOf(10)
    }
    AppSliderWithLabel(
        sliderValue, onValueChange = { newValue -> sliderValue = newValue }, valueRange = 0f..24f
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSliderWithLabel(
    value: Int, valueRange: ClosedFloatingPointRange<Float>, onValueChange: (Int) -> Unit
) {

    val primary = MaterialTheme.colorScheme.primary.toArgb()
    val onPrimary = MaterialTheme.colorScheme.onPrimary.toArgb()

    Slider(colors = SliderDefaults.colors(
        thumbColor = Color(primary), activeTrackColor = Color(primary)
    ),
        value = value.toFloat(),
        onValueChange = {
            onValueChange(it.toInt())
        },
        valueRange = valueRange,
        modifier = Modifier
            .fillMaxWidth(),
        thumb = {
            Box(
                modifier = Modifier.padding(vertical = 15.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .background(shape = CircleShape, color = Color(primary))
                        .size(45.dp)
                        .wrapContentSize(Alignment.Center),
                    text = "$value%",
                    textAlign = TextAlign.Center,
                    color = Color(onPrimary)
                )
            }
        })

}