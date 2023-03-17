package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import hc.candlecraft.candlecraftultimate.common.MeasuringUnit

/**
 * items : list of items to be render
 * defaultSelectedItemIndex : to highlight item by default (Optional)
 * useFixedWidth : set true if you want to set fix width to item (Optional)
 * itemWidth : Provide item width if useFixedWidth is set to true (Optional)
 * cornerRadius : To make control as rounded (Optional)
 * color : Set color to control (Optional)
 * onItemSelection : Get selected item index
 */
@Composable
fun SegmentedControl(
    modifier: Modifier,
    items: List<MeasuringUnit> = listOf(MeasuringUnit.Grams, MeasuringUnit.Ounces, MeasuringUnit.Percentage),
    defaultSelectedItemIndex: Int = 0,
    useFixedWidth: Boolean = false,
    itemWidth: Dp = 130.dp,
    cornerRadius : Int = 35,
    onItemSelection: (selectedItemType: MeasuringUnit) -> Unit = {}
) {

    val focusedBorderColor: Color = MaterialTheme.colorScheme.primary
    val unfocusedBorderColor = Color(0xFF85746b)

    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        if (useFixedWidth) {
                            Modifier
                                .weight(1F).heightIn(min = 50.dp)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.value == 0) 1f else 0f)
                        } else {
                            Modifier
                                .wrapContentSize().heightIn(min = 50.dp)
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex.value == 0) 1f else 0f)
                        }
                    }
                    else -> {
                        if (useFixedWidth)
                            Modifier
                                .weight(1F).heightIn(min = 50.dp)
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex.value == index) 1f else 0f)
                        else Modifier
                            .wrapContentSize().heightIn(min = 50.dp)
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedIndex.value == index) 1f else 0f)
                    }
                },
                onClick = {
                    selectedIndex.value = index
                    onItemSelection(items[selectedIndex.value])
                },
                shape = when (index) {
                    /**
                     * left outer button
                     */
                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )
                    /**
                     * right outer button
                     */
                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    /**
                     * middle button
                     */
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border =
                if (selectedIndex.value == index) {
                    BorderStroke(
                        2.dp, focusedBorderColor)
                } else {
                    BorderStroke(
                        1.dp, unfocusedBorderColor
                    )
                },
                colors = if (selectedIndex.value == index) {
                    /**
                     * selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = focusedBorderColor
                    )
                } else {
                    /**
                     * not selected colors
                     */
                    ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                },
            ) {
                Text(
                    text = stringResource(id = item.name),
                    fontWeight = FontWeight.Normal,
                    color = if (selectedIndex.value == index) {
                        MaterialTheme.colorScheme.inverseOnSurface
                    } else {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    },
                )
            }
        }
    }
}