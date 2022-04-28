package companion.battery.ady.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit

@Composable
fun SurfaceText(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit
) {

    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onSurface
    )

}

@Composable
fun BackgroundText(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit
) {

    Text(
        modifier = modifier,
        text = text,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onBackground
    )

}