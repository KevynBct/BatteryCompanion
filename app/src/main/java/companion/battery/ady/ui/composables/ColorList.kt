package companion.battery.ady.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ColorItemList() {

    Column(horizontalAlignment = Alignment.Start) {

        ColorItem(color = MaterialTheme.colorScheme.primary, text = "primary")
        ColorItem(color = MaterialTheme.colorScheme.onPrimary, text = "onPrimary")
        ColorItem(color = MaterialTheme.colorScheme.primaryContainer, text = "primaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.onPrimaryContainer, text = "onPrimaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.inversePrimary, text = "inversePrimary")
        ColorItem(color = MaterialTheme.colorScheme.secondary, text = "secondary")
        ColorItem(color = MaterialTheme.colorScheme.onSecondary, text = "onSecondary")
        ColorItem(color = MaterialTheme.colorScheme.secondaryContainer, text = "secondaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.onSecondaryContainer, text = "onSecondaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.tertiary, text = "tertiary")
        ColorItem(color = MaterialTheme.colorScheme.onTertiary, text = "onTertiary")
        ColorItem(color = MaterialTheme.colorScheme.tertiaryContainer, text = "tertiaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.onTertiaryContainer, text = "onTertiaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.background, text = "background")
        ColorItem(color = MaterialTheme.colorScheme.onBackground, text = "onBackground")
        ColorItem(color = MaterialTheme.colorScheme.surface, text = "surface")
        ColorItem(color = MaterialTheme.colorScheme.onSurface, text = "onSurface")
        ColorItem(color = MaterialTheme.colorScheme.surfaceVariant, text = "surfaceVariant")
        ColorItem(color = MaterialTheme.colorScheme.onSurfaceVariant, text = "onSurfaceVariant")
        ColorItem(color = MaterialTheme.colorScheme.surfaceTint, text = "surfaceTint")
        ColorItem(color = MaterialTheme.colorScheme.inverseSurface, text = "inverseSurface")
        ColorItem(color = MaterialTheme.colorScheme.inverseOnSurface, text = "inverseOnSurface")
        ColorItem(color = MaterialTheme.colorScheme.error, text = "error")
        ColorItem(color = MaterialTheme.colorScheme.onError, text = "onError")
        ColorItem(color = MaterialTheme.colorScheme.errorContainer, text = "errorContainer")
        ColorItem(color = MaterialTheme.colorScheme.onErrorContainer, text = "onErrorContainer")
        ColorItem(color = MaterialTheme.colorScheme.outline, text = "outline")

    }

}

@Composable
fun ColorItem(color: Color, text: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(modifier = Modifier
            .size(80.dp)
            .background(color = color))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )

    }


}