package hc.candlecraft.candlecraftultimate.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlin.Unit

@Composable
fun CustomButton(
    text: String,
    icon: ImageVector? = null,
    iconDescription: String? = null,
    onClick: () -> Unit = { }
) {
    Button(onClick = onClick, shape = RoundedCornerShape(15.dp)) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
        }
        Text(modifier = Modifier.padding(bottom = 5.dp), text = text)
    }
}