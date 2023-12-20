package hc.candlecraft.candlecraftultimate.feature_recipe.presentation.add_edit_recipe.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hc.candlecraft.candlecraftultimate.R

@Composable
fun ImageContainer(
    modifier: Modifier = Modifier,
    onClickAction: () -> Unit = {},
    content: @Composable () -> Unit,

) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.wrapContentSize()
                .background(
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(15.dp))
                .border(
                    2.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(15.dp)
                )
                .clickable { onClickAction() }
        ) {
            content()
        }
}

@Preview
@Composable
private fun ImageContainerPreview() {
    ImageContainer(modifier = Modifier.height(200.dp) , onClickAction = {}, content = {
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.candle_image_test),
            contentDescription = null
        )
    })
}

@Preview
@Composable
private fun ImageContainerPreview2() {
    ImageContainer(onClickAction = {}, content = {
        Icon(
            modifier = Modifier
                .padding(20.dp)
                .size(45.dp),
            imageVector = Icons.Default.CameraAlt,
            contentDescription = stringResource(id = R.string.add_ingredient)
        )
    })
}